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

/**
 * Represents implementation of service interface with Question entity.
 * T parameter is provided with QuestionDTO.
 *
 * @author Vuk Manojlovic
 */
@Service
public class QuestionServiceImpl implements ServiceInterface<QuestionDTO> {

    /**
     * Reference variable of QuestionRepository class.
     */
    private QuestionRepository questionRepository;

    /**
     * Reference variable of QuestionTestRepository class.
     */
    private QuestionTestRepository questionTestRepository;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository,QuestionTestRepository questionTestRepository,ModelMapper modelMapper){
        this.questionRepository = questionRepository;
        this.questionTestRepository = questionTestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QuestionDTO> findAll() {
        return questionRepository.findAll().stream().map(question->modelMapper.map(question,QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO findById(Object id) throws NotFoundException {
        Optional<Question> question = questionRepository.findById((Integer) id);
        QuestionDTO questionDTO;
        if(question.isPresent()){
            questionDTO = modelMapper.map(question.get(),QuestionDTO.class);
            return questionDTO;
        }
        else {
            throw new NotFoundException("Did not find Question with id: " + id);
        }
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
        if(!(questionRepository.findById((Integer)id).isPresent())){
            throw new NotFoundException("Did not find Question with id: " + id);
        }
        questionRepository.deleteById((Integer)id);
    }

    /**
     * Retrieves all questions that test have and points
     * for each question as list of QuestionTest objects.
     * QuestionTest objects are mapped in DTO form.
     * Method is called from TestController.
     *
     * @param testId of test whose questions and points are needed
     * @return list of QuestionTestDTO objects
     * @throws NotFoundException if QuestionTest entities with given test id does not exist in database
     */
    public List<QuestionTestDTO> getQuestions(Integer testId) throws NotFoundException {
        List<QuestionTest> questionTests = questionTestRepository.findByTestId(testId);
        if(questionTests.isEmpty()){
            throw new NotFoundException("Did not find QuestionsTest with testId: " + testId);
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
