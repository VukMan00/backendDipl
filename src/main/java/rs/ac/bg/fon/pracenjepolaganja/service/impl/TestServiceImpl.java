package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ProfessorRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionTestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.TestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.QuestionTest;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents implementation of service interface with Test entity.
 * T parameter is provided with TestDTO.
 *
 * @author Vuk Manojlovic
 */
@Service
public class TestServiceImpl implements ServiceInterface<TestDTO> {

    /**
     * Reference variable of TestRepository class.
     */
    private TestRepository testRepository;

    /**
     * Reference variable of QuestionTestRepository class.
     */
    private QuestionTestRepository questionTestRepository;

    /**
     * Reference variable of ProfessorRepository class.
     */
    private ProfessorRepository professorRepository;

    /**
     * Reference variable of ExamRepository class.
     */
    private ExamRepository examRepository;

    /**
     * Reference variable of QuestionService class.
     */
    private QuestionServiceImpl questionService;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    @Autowired
    public TestServiceImpl(TestRepository testRepository, QuestionTestRepository questionTestRepository,ProfessorRepository professorRepository, ModelMapper modelMapper,QuestionServiceImpl questionService, ExamRepository examRepository){
        this.testRepository = testRepository;
        this.questionTestRepository = questionTestRepository;
        this.modelMapper = modelMapper;
        this.professorRepository = professorRepository;
        this.questionService = questionService;
        this.examRepository = examRepository;
    }

    @Override
    public List<TestDTO> findAll() {
        List<Test> tests = testRepository.findAll();
        List<TestDTO> testsDTO = new ArrayList<>();
        for(Test test:tests){
            ProfessorDTO professorDTO = modelMapper.map(test.getAuthor(),ProfessorDTO.class);
            TestDTO testDTO = modelMapper.map(test,TestDTO.class);
            testDTO.setAuthor(professorDTO);
            testsDTO.add(testDTO);
        }
        return testsDTO;
    }

    @Override
    public TestDTO findById(Object id) throws NotFoundException {
        Optional<Test> test = testRepository.findById((Integer) id);
        TestDTO testDTO;
        if(test.isPresent()){
            ProfessorDTO professorDTO = modelMapper.map(test.get().getAuthor(),ProfessorDTO.class);
            testDTO = modelMapper.map(test.get(),TestDTO.class);
            testDTO.setAuthor(professorDTO);
            Collection<QuestionTestDTO> questionsTestDTO = questionService.getQuestionsTest((Integer) id);
            testDTO.setQuestions(questionsTestDTO);
        }
        else{
            throw new NotFoundException("Test nije pronadjen");
        }
        return testDTO;
    }

    @Override
    public TestDTO save(TestDTO testDTO) throws NotFoundException {
        if(testDTO==null){
            throw new NullPointerException("Test ne moze biti null");
        }
        if(professorRepository.findById(testDTO.getAuthor().getId()).isPresent()) {
            Test test = modelMapper.map(testDTO,Test.class);
            if(testDTO.getQuestions()!=null){
                Collection<QuestionTest> questionsTest = testDTO.getQuestions().stream().map(questionTestDTO -> modelMapper.map(questionTestDTO, QuestionTest.class))
                        .collect(Collectors.toList());
                test.setQuestionTestCollection(questionsTest);
            }
            Test savedTest = testRepository.save(test);
            return modelMapper.map(savedTest, TestDTO.class);
        }
        else{
            throw new NotFoundException("Autor testa nije pronadjen");
        }
    }

    @Override
    public void deleteById(Object id) throws Exception {
        Optional<Test> test = testRepository.findById((Integer) id);
        if(!test.isPresent()){
            throw new NotFoundException("Test nije pronadjen");
        }
        if(hasAssociatedChildren(test.get())){
            throw new IllegalStateException("Test ne moze da se izbrise");
        }
        testRepository.deleteById((Integer)id);
    }

    private boolean hasAssociatedChildren(Test test) {
        if(examRepository.findByTestId(test.getId()).isEmpty()) {
            return false;
        }
        else{
            return true;
        }
    }

    public List<TestDTO> getTests(Integer professorId) throws NotFoundException {
        List<TestDTO> tests = testRepository.findByAuthor(professorId).stream().map(test->modelMapper.map(test,TestDTO.class))
                .collect(Collectors.toList());
        if(tests.isEmpty()){
            throw new NotFoundException("Nisu pronadjeni testovi profesora sa id-em: " + professorId);
        }
        return tests;
    }

    /**
     * Saves the questionTest entity in database.
     * QuestionTest entity is mapped in DTO form.
     * Connects one test to another question.
     *
     * @param questionTestDTO questionTest in DTO form that needs to be saved
     * @return saved questionTest entity in DTO form
     * @throws NullPointerException if provided questionTestDTO is null
     */
    public QuestionTestDTO saveQuestionTest(QuestionTestDTO questionTestDTO) {
        if(questionTestDTO==null){
            throw new NullPointerException("QuestionTest ne moze biti null");
        }
        QuestionTest questionTest = questionTestRepository.save(modelMapper.map(questionTestDTO,QuestionTest.class));
        return modelMapper.map(questionTest,QuestionTestDTO.class);
    }

    /**
     * Deletes question from test, questionTest entity.
     *
     * @param testId id of test whose question is going to be deleted
     * @param questionId id of question that is going to be deleted
     * @throws NotFoundException if QuestionTest with given ids does not exist in database
     */
    public void deleteQuestionTest(Integer testId, Integer questionId) throws NotFoundException {
        if(!questionTestRepository.findById(new QuestionTestPK(questionId,testId)).isPresent()){
            throw new NotFoundException("Nije pronadjeno pitanje sa id-em: " + questionId + " u testu sa id-em: " + testId);
        }
        questionTestRepository.deleteById(new QuestionTestPK(questionId,testId));
    }

    /**
     * Retrieves all tests where question belongs and points
     * that question has in each test as list of QuestionTest objects.
     * QuestionTest objects are mapped in DTO form.
     * Method is called from QuestionController.
     *
     * @param questionId id of question whose points and tests are needed
     * @return list of QuestionTestDTO objects
     * @throws NotFoundException if QuestionTest entities with given question id does not exist in database.
     */
    public List<QuestionTestDTO> getTestsFromQuestion(Integer questionId) throws NotFoundException {
        List<QuestionTest> questionTests = questionTestRepository.findByQuestionId(questionId);
        if (questionTests.isEmpty()) {
            return new ArrayList<>();
        }

        List<QuestionTestDTO> questionTestDTOs = new ArrayList<>();
        for (QuestionTest questionTest : questionTests) {
            QuestionDTO questionDTO = modelMapper.map(questionTest.getQuestion(), QuestionDTO.class);
            TestDTO testDTO = modelMapper.map(questionTest.getTest(), TestDTO.class);
            QuestionTestDTO questionTestDTO = modelMapper.map(questionTest, QuestionTestDTO.class);

            questionTestDTO.setQuestion(questionDTO);
            questionTestDTO.setTest(testDTO);
            questionTestDTOs.add(questionTestDTO);
        }
        return questionTestDTOs;
    }
}
