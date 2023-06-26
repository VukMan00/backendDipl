package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionTestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.TestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.QuestionTest;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
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
     * Reference variable of ProfessorServiceImpl class.
     */
    private ProfessorServiceImpl professorService;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    @Autowired
    public TestServiceImpl(TestRepository testRepository, QuestionTestRepository questionTestRepository,ProfessorServiceImpl professorService, ModelMapper modelMapper){
        this.testRepository = testRepository;
        this.questionTestRepository = questionTestRepository;
        this.modelMapper = modelMapper;
        this.professorService = professorService;
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
        }
        else{
            throw new NotFoundException("Did not find Test with id: " + id);
        }
        return testDTO;
    }

    @Override
    public TestDTO save(TestDTO testDTO) throws NotFoundException {
        if(testDTO==null){
            throw new NullPointerException("Test can't be null");
        }
        if(professorService.findById(testDTO.getAuthor().getId())!=null) {
            Test test = testRepository.save(modelMapper.map(testDTO, Test.class));
            return modelMapper.map(test, TestDTO.class);
        }
        else{
            throw new NotFoundException("Did not find professor with id: " + testDTO.getAuthor().getId());
        }
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if(!testRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Did not find Test with id: " + id);
        }
        testRepository.deleteById((Integer)id);
    }

    public List<TestDTO> getTests(Integer professorId) throws NotFoundException {
        List<TestDTO> tests = testRepository.findByAuthor(professorId).stream().map(test->modelMapper.map(test,TestDTO.class))
                .collect(Collectors.toList());
        if(tests.isEmpty()){
            throw new NotFoundException("Did not find Tests with professorId: " + professorId);
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
            throw new NullPointerException("QuestionTest can't be null");
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
            throw new NotFoundException("Did not find QuestionTest with id: " + new QuestionTestPK(questionId,testId));
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
        if(questionTests.isEmpty()){
            throw new NotFoundException("Did not find QuestionTest with testId: " + questionId);
        }

        List<QuestionTestDTO> questionTestDTOs = new ArrayList<>();
        for(QuestionTest questionTest:questionTests){
            QuestionDTO questionDTO = modelMapper.map(questionTest.getQuestion(),QuestionDTO.class);
            TestDTO testDTO = modelMapper.map(questionTest.getTest(),TestDTO.class);
            QuestionTestDTO questionTestDTO = modelMapper.map(questionTest,QuestionTestDTO.class);

            questionTestDTO.setQuestion(questionDTO);
            questionTestDTO.setTest(testDTO);
            questionTestDTOs.add(questionTestDTO);
        }
        return questionTestDTOs;
    }
}
