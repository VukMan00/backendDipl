package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionTestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.TestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionTestDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.ResultExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.TestDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.QuestionTest;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private TestRepository testRepository;

    @Mock
    private QuestionTestRepository questionTestRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private TestServiceImpl testService;

    private rs.ac.bg.fon.pracenjepolaganja.entity.Test test;

    private Professor professor;

    private Question question;

    private QuestionTest questionTest;

    @BeforeEach
    void setUp(){
        question = Question.builder()
                .id(1)
                .content("Is Java object oriented")
                .build();

        professor = Professor.builder()
                .id(1)
                .name("Dusan")
                .lastname("Savic")
                .email("dusan.savic@fon.bg.ac.rs")
                .build();

        test = rs.ac.bg.fon.pracenjepolaganja.entity.Test.builder()
                .id(1)
                .content("Java test")
                .author(professor)
                .build();

        questionTest = QuestionTest.builder()
                .points(30)
                .question(question)
                .test(test)
                .build();
    }

    @Test
    void testFindAll() {
        rs.ac.bg.fon.pracenjepolaganja.entity.Test test1 = rs.ac.bg.fon.pracenjepolaganja.entity.Test.builder()
                .id(2)
                .content("Java test for beginners")
                .author(professor)
                .build();

        given(testRepository.findAll()).willReturn(List.of(test,test1));

        List<TestDTO> tests = testService.findAll();

        assertThat(tests).isNotNull();
        assertThat(tests.size()).isEqualTo(2);
        verify(testRepository,times(1)).findAll();
    }

    @Test
    void testFindById() throws NotFoundException {
        given(testRepository.findById(test.getId())).willReturn(Optional.ofNullable(test));

        TestDTO testDTO = testService.findById(test.getId());

        assertThat(testDTO).isNotNull();
        verify(testRepository,times(1)).findById(test.getId());
    }

    @Test
    void testFindByIdNotFound(){
        Integer testId = 2;
        given(testRepository.findById(testId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            testService.findById(testId);
        });

        verify(testRepository,times(1)).findById(testId);
    }

    @Test
    void testSave() {
        given(testRepository.save(test)).willReturn(test);

        TestDTO savedTestDTO = testService.save(modelMapper.map(test,TestDTO.class));

        assertThat(savedTestDTO).isNotNull();
        verify(testRepository,times(1)).save(test);
    }

    @Test
    void testSaveNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            testService.save(null);
        });
    }

    @Test
    void testDeleteById() throws NotFoundException {
        given(testRepository.findById(test.getId())).willReturn(Optional.ofNullable(test));
        willDoNothing().given(testRepository).deleteById(test.getId());

        testService.deleteById(test.getId());

        verify(testRepository,times(1)).deleteById(test.getId());
    }

    @Test
    void testDeleteByIdNotFound(){
        Integer testId = 2;
        given(testRepository.findById(testId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            testService.deleteById(testId);
        });

        verify(testRepository,times(1)).findById(testId);
    }

    @Test
    void testGetTestsFromProfessorId() throws NotFoundException {
        rs.ac.bg.fon.pracenjepolaganja.entity.Test test1 = rs.ac.bg.fon.pracenjepolaganja.entity.Test.builder()
                .id(2)
                .content("Java beginners test")
                .author(professor)
                .build();
        System.out.println(test1.getAuthor());
        given(testRepository.findByAuthor(professor.getId())).willReturn(List.of(test,test1));

        List<TestDTO> tests = testService.getTests(professor.getId());

        System.out.println(tests);
        assertThat(tests).isNotNull();
        assertThat(tests.size()).isEqualTo(2);

        verify(testRepository,times(1)).findByAuthor(professor.getId());
    }

    @Test
    void testGetTestsFromProfessorIdNotFound(){
        Integer professorId = 2;
        given(testRepository.findByAuthor(professorId)).willReturn(new ArrayList<>());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            testService.getTests(professorId);
        });

        verify(testRepository,times(1)).findByAuthor(professorId);
    }

    @Test
    void testSaveQuestionTest() {
        given(questionTestRepository.save(questionTest)).willReturn(questionTest);

        QuestionTestDTO savedQuestionTestDTO = testService.saveQuestionTest(modelMapper.map(questionTest,QuestionTestDTO.class));

        assertThat(savedQuestionTestDTO).isNotNull();
        verify(questionTestRepository,times(1)).save(questionTest);
    }

    @Test
    void testSaveQuestionTestNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            testService.saveQuestionTest(null);
        });
    }

    @Test
    void testDeleteQuestionTest() throws NotFoundException {
        QuestionTestPK questionTestPK = new QuestionTestPK(question.getId(),test.getId());
        given(questionTestRepository.findById(questionTestPK)).willReturn(Optional.ofNullable(questionTest));
        willDoNothing().given(questionTestRepository).deleteById(questionTestPK);

        testService.deleteQuestionTest(test.getId(),question.getId());

        verify(questionTestRepository,times(1)).deleteById(questionTestPK);
    }

    @Test
    void testDeleteQuestionTestNotFound(){
        QuestionTestPK questionTestPK = new QuestionTestPK(2,2);
        given(questionTestRepository.findById(questionTestPK)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            testService.deleteQuestionTest(2,2);
        });

        verify(questionTestRepository,times(1)).findById(questionTestPK);
    }

    @Test
    void testGetTestsFromQuestion() throws NotFoundException {
        rs.ac.bg.fon.pracenjepolaganja.entity.Test test1 = rs.ac.bg.fon.pracenjepolaganja.entity.Test.builder()
                .id(2)
                .content("Java test for beginners")
                .author(professor)
                .build();

        QuestionTest questionTest1 = QuestionTest.builder()
                .questionTestPK(new QuestionTestPK(question.getId(),test1.getId()))
                .points(25)
                .question(question)
                .test(test1)
                .build();

        given(questionTestRepository.findByQuestionId(question.getId())).willReturn(List.of(questionTest,questionTest1));

        List<QuestionTestDTO> tests = testService.getTestsFromQuestion(question.getId());

        assertThat(tests).isNotNull();
        assertThat(tests.size()).isEqualTo(2);
        assertThat(tests.get(0).getQuestion().getId()).isEqualTo(question.getId());
        assertThat(tests.get(1).getQuestion().getId()).isEqualTo(question.getId());

        verify(questionTestRepository,times(1)).findByQuestionId(question.getId());
    }

    @Test
    void testGetTestsFromQuestionNotFound(){
        Integer questionId = 2;
        given(questionTestRepository.findByQuestionId(questionId)).willReturn(new ArrayList<>());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            testService.getTestsFromQuestion(questionId);
        });

        verify(questionTestRepository,times(1)).findByQuestionId(questionId);
    }
}