package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class TestServiceImpl implements ServiceInterface<TestDTO> {

    private TestRepository testRepository;

    private QuestionTestRepository questionTestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TestServiceImpl(TestRepository testRepository, QuestionTestRepository questionTestRepository){
        this.testRepository = testRepository;
        this.questionTestRepository = questionTestRepository;
    }

    @Override
    public List<TestDTO> findAll() {
        List<Test> tests = testRepository.findAll();
        List<TestDTO> testsDTO = new ArrayList<>();
        for(Test test:tests){
            ProfessorDTO professorDTO = modelMapper.map(test.getAuthor(),ProfessorDTO.class);
            TestDTO testDTO = modelMapper.map(test,TestDTO.class);
            testDTO.setProfessor(professorDTO);
            testsDTO.add(testDTO);
        }
        return testsDTO;
    }

    @Override
    public TestDTO findById(Object id) throws NotFoundException {
        if((Integer)id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        Optional<Test> test = testRepository.findById((Integer) id);
        TestDTO testDTO;
        if(test.isPresent()){
            ProfessorDTO professorDTO = modelMapper.map(test.get().getAuthor(),ProfessorDTO.class);
            testDTO = modelMapper.map(test.get(),TestDTO.class);
            testDTO.setProfessor(professorDTO);
        }
        else{
            throw new NotFoundException("Did not find Test with id: " + id);
        }
        return testDTO;
    }

    @Override
    public TestDTO save(TestDTO testDTO) {
        if(testDTO==null){
            throw new NullPointerException("Test can't be null");
        }
        Test test = testRepository.save(modelMapper.map(testDTO,Test.class));
        return modelMapper.map(test,TestDTO.class);
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if((Integer)id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        if(!testRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Did not find Test with id: " + id);
        }
        testRepository.deleteById((Integer)id);
    }

    public List<TestDTO> getTests(Integer professorId) throws NotFoundException {
        if(professorId<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        List<TestDTO> tests = testRepository.findByAuthor(professorId).stream().map(test->modelMapper.map(test,TestDTO.class))
                .collect(Collectors.toList());
        if(tests.isEmpty()){
            throw new NotFoundException("Did not find Tests with professorId: " + professorId);
        }
        return tests;
    }

    public QuestionTestDTO saveQuestionTest(QuestionTestDTO questionTestDTO) {
        if(questionTestDTO==null){
            throw new NullPointerException("QuestionTest can't be null");
        }
        QuestionTest questionTest = questionTestRepository.save(modelMapper.map(questionTestDTO,QuestionTest.class));
        return modelMapper.map(questionTest,QuestionTestDTO.class);
    }

    public void deleteQuestionTest(Integer testId, Integer questionId) throws NotFoundException {
        if(testId<0 || questionId<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        if(!questionTestRepository.findById(new QuestionTestPK(questionId,testId)).isPresent()){
            throw new NotFoundException("Did not find QuestionTest with id: " + new QuestionTestPK(questionId,testId));
        }
        questionTestRepository.deleteById(new QuestionTestPK(questionId,testId));
    }

    public List<QuestionTestDTO> getQuestions(Integer questionId) throws NotFoundException {
        if(questionId<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        List<QuestionTest> questionTests = questionTestRepository.findByQuestionId(questionId);
        if(questionTests.isEmpty()){
            throw new NotFoundException("Did not find QuestionsTest with questionId: " + questionId);
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
