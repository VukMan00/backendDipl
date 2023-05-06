package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionTestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionTestDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.TestDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.QuestionTest;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements ServiceInterface<QuestionDTO> {

    private QuestionRepository questionRepository;

    private QuestionTestRepository questionTestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository,QuestionTestRepository questionTestRepository){
        this.questionRepository = questionRepository;
        this.questionTestRepository = questionTestRepository;
    }

    @Override
    public List<QuestionDTO> findAll() {
        return questionRepository.findAll().stream().map(question->modelMapper.map(question,QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO findById(Object id) throws NotFoundException {
        if((Integer)id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        Optional<Question> question = questionRepository.findById((Integer) id);
        QuestionDTO questionDTO;
        if(question.isPresent()){
            questionDTO = modelMapper.map(question.get(),QuestionDTO.class);
        }
        else {
            throw new NotFoundException("Did not find Question with id: " + id);
        }
        return questionDTO;
    }

    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        if(questionDTO == null){
            throw new NullPointerException("Question can't be null");
        }
        Question question = questionRepository.save(modelMapper.map(questionDTO,Question.class));
        return modelMapper.map(question,QuestionDTO.class);
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if((Integer)id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        if(!questionRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Did not find Question with id: " + id);
        }
        questionRepository.deleteById((Integer)id);
    }

    public List<QuestionTestDTO> getTests(Integer testId) throws NotFoundException {
        if(testId<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        List<QuestionTest> questionTests = questionTestRepository.findByTestId(testId);
        if(questionTests.isEmpty()){
            throw new NotFoundException("Did not find QuestionTest with testId: " + testId);
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
