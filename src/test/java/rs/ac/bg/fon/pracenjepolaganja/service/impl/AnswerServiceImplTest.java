package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import rs.ac.bg.fon.pracenjepolaganja.dao.AnswerRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.ProfessorDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AnswerServiceImplTest{

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private AnswerServiceImpl answerService;

    @Spy
    private ModelMapper modelMapper;

    private Answer answer;

    private Question question;

    @BeforeEach
    void setUp() {
        question = Question.builder()
                .id(1)
                .content("Is Java object-oriented programing language")
                .build();

        answer = Answer.builder()
                .answerPK(new AnswerPK(1,1))
                .content("Yes")
                .solution(true)
                .question(question)
                .build();
    }

    @Test
    void testFindAll(){
        Answer answer2 = Answer.builder()
                .answerPK(new AnswerPK(2,1))
                .content("No")
                .solution(false)
                .question(question)
                .build();
        given(answerRepository.findAll()).willReturn(List.of(answer,answer2));

        List<AnswerDTO> answers = answerService.findAll();

        assertThat(answers).isNotNull();
        assertThat(answers.size()).isEqualTo(2);
        verify(answerRepository,times(1)).findAll();
    }

    @Test
    void testFindById() throws NotFoundException {
        AnswerPK answerPK = new AnswerPK(1,1);
        given(answerRepository.findById(answerPK)).willReturn(Optional.ofNullable(answer));

        AnswerDTO answerDTO = answerService.findById(answerPK);

        assertThat(answerDTO).isNotNull();
        verify(answerRepository,times(1)).findById(answerPK);
    }

    @Test
    void testFindByIdNotFound(){
        AnswerPK answerPK = new AnswerPK(3,1);
        given(answerRepository.findById(answerPK)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            answerService.findById(answerPK);
        });

        verify(answerRepository,times(1)).findById(answerPK);
    }

    @Test
    void testSave() throws NotFoundException {
        given(answerRepository.save(answer)).willReturn(answer);
        given(questionRepository.findById(answer.getAnswerPK().getQuestionId())).willReturn(Optional.ofNullable(question));

        AnswerDTO savedAnswerDTO = answerService.save(modelMapper.map(answer,AnswerDTO.class));

        assertThat(savedAnswerDTO).isNotNull();
        verify(answerRepository,times(1)).save(answer);
        verify(questionRepository,times(1)).findById(answer.getAnswerPK().getQuestionId());
    }

    @Test
    void testSaveQuestionNotFound() throws NotFoundException {
        AnswerPK answerPK = new AnswerPK(3,1);
        answer.setAnswerPK(answerPK);
        given(questionRepository.findById(answerPK.getQuestionId())).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            answerService.save(modelMapper.map(answer,AnswerDTO.class));
        });

        verify(questionRepository,times(1)).findById(answerPK.getQuestionId());

    }



    @Test
    void testSaveNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            answerService.save(null);
        });
    }

    @Test
    void testDeleteById() throws NotFoundException {
        AnswerPK answerPK = new AnswerPK(1,1);
        given(answerRepository.findById(answerPK)).willReturn(Optional.ofNullable(answer));
        willDoNothing().given(answerRepository).deleteById(answerPK);

        answerService.deleteById(answerPK);

        verify(answerRepository,times(1)).deleteById(answerPK);
        verify(answerRepository,times(1)).findById(answerPK);
    }

    @Test
    void testDeleteByIdNotFound(){
        AnswerPK answerPK = new AnswerPK(3,1);
        given(answerRepository.findById(answerPK)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            answerService.deleteById(answerPK);
        });

        verify(answerRepository,times(1)).findById(answerPK);
    }

    @Test
    void testGetAnswers() throws NotFoundException {
        Answer answer2 = Answer.builder()
                .answerPK(new AnswerPK(2,1))
                .content("No")
                .solution(false)
                .question(question)
                .build();

        given(answerRepository.findByQuestionId(question.getId())).willReturn(List.of(answer,answer2));

        List<AnswerDTO> answers = answerService.getAnswers(question.getId());

        assertThat(answers).isNotNull();
        assertThat(answers.size()).isEqualTo(2);
        assertThat(answers.get(0).getQuestion().getId()).isEqualTo(question.getId());
        assertThat(answers.get(1).getQuestion().getId()).isEqualTo(question.getId());

        verify(answerRepository,times(1)).findByQuestionId(question.getId());
    }

    @Test
    void testGetAnswersQuestionNotFound(){
        Integer questionId = 3;
        given(answerRepository.findByQuestionId(questionId)).willReturn(new ArrayList<>());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            answerService.getAnswers(questionId);
        });

        verify(answerRepository,times(1)).findByQuestionId(questionId);
    }

}