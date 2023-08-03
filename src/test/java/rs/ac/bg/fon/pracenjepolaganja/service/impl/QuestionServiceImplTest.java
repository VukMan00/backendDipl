package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionTestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionTestDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.QuestionTest;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionTestRepository questionTestRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Question question;


    @BeforeEach
    void setUp() {
        question = Question.builder()
                .id(1)
                .content("Is Java object oriented")
                .build();
    }

    @Test
    void testFindAll() {
        Question question1 = Question.builder()
                .id(2)
                .content("Is Java platform independent")
                .build();
        given(questionRepository.findAll()).willReturn(List.of(question,question1));

        List<QuestionDTO> questions = questionService.findAll();

        assertThat(questions).isNotNull();
        assertThat(questions.size()).isEqualTo(2);
        verify(questionRepository,times(1)).findAll();
    }

    @Test
    void testFindById() throws NotFoundException {
        given(questionRepository.findById(question.getId())).willReturn(Optional.ofNullable(question));

        QuestionDTO questionDTO = questionService.findById(question.getId());

        assertThat(questionDTO).isNotNull();
        verify(questionRepository,times(1)).findById(question.getId());
    }

    @Test
    void testFindByIdNotFound(){
        Integer questionId = 2;
        given(questionRepository.findById(questionId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            questionService.findById(questionId);
        });

        verify(questionRepository,times(1)).findById(questionId);
    }

    @Test
    void testSave() throws Exception {
        given(questionRepository.save(question)).willReturn(question);

        QuestionDTO savedQuestionDTO = questionService.save(modelMapper.map(question,QuestionDTO.class));

        assertThat(savedQuestionDTO).isNotNull();
        verify(questionRepository,times(1)).save(question);
    }

    @Test
    void testSaveNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            questionService.save(null);
        });
    }

    @Test
    void testDeleteById() throws NotFoundException {
        given(questionRepository.findById(question.getId())).willReturn(Optional.ofNullable(question));
        willDoNothing().given(questionRepository).deleteById(question.getId());

        questionService.deleteById(question.getId());

        verify(questionRepository,times(1)).deleteById(question.getId());
    }

    @Test
    void testDeleteByIdNotFound(){
        Integer questionId = 2;
        given(questionRepository.findById(questionId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            questionService.deleteById(questionId);
        });

        verify(questionRepository,times(1)).findById(questionId);
    }

    @Test
    void testGetQuestionsFromTestId() throws NotFoundException {
        Question question1 = Question.builder()
                .id(2)
                .content("Is java platform independent")
                .build();
        rs.ac.bg.fon.pracenjepolaganja.entity.Test test = rs.ac.bg.fon.pracenjepolaganja.entity.Test.builder()
                .id(1)
                .content("Java first test")
                .build();

        QuestionTest questionTest = QuestionTest.builder()
                .questionTestPK(new QuestionTestPK(1,1))
                .points(11)
                .question(question)
                .test(test)
                .build();
        QuestionTest questionTest1 = QuestionTest.builder()
                .questionTestPK(new QuestionTestPK(2,1))
                .points(10)
                .question(question1)
                .test(test)
                .build();

        given(questionTestRepository.findByTestId(test.getId())).willReturn(List.of(questionTest,questionTest1));

        List<QuestionTestDTO> questions = questionService.getQuestionsTest(test.getId());

        assertThat(questions).isNotNull();
        assertThat(questions.size()).isEqualTo(2);
        assertThat(questions.get(0).getTest().getId()).isEqualTo(test.getId());
        assertThat(questions.get(1).getTest().getId()).isEqualTo(test.getId());

        verify(questionTestRepository,times(1)).findByTestId(test.getId());
    }

    @Test
    void testGetQuestionsFromTestNotFound(){
        Integer testId = 2;
        given(questionTestRepository.findByTestId(testId)).willReturn(new ArrayList<>());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            questionService.getQuestionsTest(testId);
        });

        verify(questionTestRepository,times(1)).findByTestId(testId);
    }
}