package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionTestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.TestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.entity.QuestionTest;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;
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
    public TestDTO findById(Object id) {
        Optional<Test> test = testRepository.findById((Integer) id);

        Test theTest = null;
        TestDTO testDTO = null;
        if(test.isPresent()){
            theTest = test.get();
            ProfessorDTO professorDTO = modelMapper.map(theTest.getAuthor(),ProfessorDTO.class);
            testDTO = modelMapper.map(theTest,TestDTO.class);
            testDTO.setProfessor(professorDTO);
        }
        else{
            throw new RuntimeException("Did not find Test with id - " + (Integer)id);
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
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        testRepository.deleteById(id);
    }

    public List<TestDTO> getTests(Integer professorId) {
        return testRepository.findByAuthor(professorId).stream().map(test->modelMapper.map(test,TestDTO.class))
                .collect(Collectors.toList());
    }

    public QuestionTestDTO saveQuestionTest(QuestionTestDTO questionTestDTO) {
        if(questionTestDTO==null){
            throw new NullPointerException("QuestionTest can't be null");
        }
        QuestionTest questionTest = questionTestRepository.save(modelMapper.map(questionTestDTO,QuestionTest.class));
        return modelMapper.map(questionTest,QuestionTestDTO.class);
    }

    public void deleteQuestionTest(Integer testId, Integer questionId) {
        if(testId<0 || questionId<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        questionTestRepository.deleteById(new QuestionTestPK(questionId,testId));
    }

    public List<QuestionTestDTO> getQuestions(Integer questionId) {
        List<QuestionTest> questionTests = questionTestRepository.findByQuestionId(questionId);
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
