package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.AnswerRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionTestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.Collection;
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
     * Reference variable of AnswerRepository class.
     */
    private AnswerRepository answerRepository;

    /**
     * Reference variable of AnswerServiceImpl class.
     */
    private AnswerServiceImpl answerService;

    /**
     * Reference variable of EntityManager class.
     */
    @Autowired
    private EntityManager entityManager;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionTestRepository questionTestRepository, ModelMapper modelMapper, AnswerRepository answerRepository,AnswerServiceImpl answerService){
        this.questionRepository = questionRepository;
        this.questionTestRepository = questionTestRepository;
        this.modelMapper = modelMapper;
        this.answerRepository = answerRepository;
        this.answerService = answerService;
    }

    @Override
    public List<QuestionDTO> findAll() {
        return questionRepository.findAll().stream().map(question->modelMapper.map(question,QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO findById(Object questionId) throws NotFoundException {
        Optional<Question> question = questionRepository.findById((Integer) questionId);
        QuestionDTO questionDTO;
        if(question.isPresent()){
            questionDTO = modelMapper.map(question.get(),QuestionDTO.class);
            Collection<AnswerDTO> answersDTO = answerService.getAnswers((Integer) questionId);
            questionDTO.setAnswers(answersDTO);
            return questionDTO;
        }
        else {
            throw new NotFoundException("Pitanje nije pronadjeno");
        }
    }

    @Override
    public QuestionDTO save(QuestionDTO questionDTO) throws Exception {
        if(questionDTO == null){
            throw new NullPointerException("Pitanje ne moze biti null");
        }
        QuestionDTO newQuestionDTO = questionDTO;

        Question question = Question.builder().content(questionDTO.getContent()).build();
        Question savedQuestion = questionRepository.save(question);

        if(newQuestionDTO.getAnswers()!=null && !newQuestionDTO.getAnswers().isEmpty()){
            Integer answerId = 0;
            for(AnswerDTO answerDTO:newQuestionDTO.getAnswers()){
                Answer answer = Answer.builder().answerPK(new AnswerPK(0,savedQuestion.getId())).content(answerDTO.getContent()).solution(answerDTO.isSolution()).build();
                if(answerId!=0){
                    answerId++;
                    answer.setAnswerPK(new AnswerPK(answerId,savedQuestion.getId()));
                }
               answerRepository.save(answer);

                Query query = entityManager.createNativeQuery("SELECT answer_id FROM answer WHERE question_id = :questionId");
                query.setParameter("questionId", answer.getAnswerPK().getQuestionId());
                List<Integer> answersId = query.getResultList();

                answerId = answersId.get(answersId.size()-1);
            }
        }
        return modelMapper.map(savedQuestion,QuestionDTO.class);
    }

    @Override
    public QuestionDTO update(QuestionDTO questionDTO) throws Exception {
        if(questionDTO == null){
            throw new NullPointerException("Pitanje ne moze biti null");
        }
        Optional<Question> dbQuestion = questionRepository.findById(questionDTO.getId());
        if(dbQuestion.isPresent()) {
            Question question = modelMapper.map(questionDTO,Question.class);
            if(questionDTO.getAnswers()!=null && !questionDTO.getAnswers().isEmpty()){
                Integer answerId = 0;
                for(AnswerDTO answerDTO:questionDTO.getAnswers()){
                    Answer answer = modelMapper.map(answerDTO,Answer.class);
                    if(answerId!=0){
                        answerId++;
                        answer.setAnswerPK(new AnswerPK(answerId,questionDTO.getId()));
                    }
                    answerRepository.save(answer);

                    Query query = entityManager.createNativeQuery("SELECT answer_id FROM answer WHERE question_id = :questionId");
                    query.setParameter("questionId", answer.getAnswerPK().getQuestionId());
                    List<Integer> answersId = query.getResultList();

                    answerId = answersId.get(answersId.size()-1);
                }
            }
            Question savedQuestion = questionRepository.save(question);
            return modelMapper.map(savedQuestion, QuestionDTO.class);
        }
        else{
            throw new NotFoundException("Pitanje nije pronadjeno");
        }
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if(!(questionRepository.findById((Integer)id).isPresent())){
            throw new NotFoundException("Pitanje nije pronadjeno");
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
    public List<QuestionTestDTO> getQuestionsTest(Integer testId) throws NotFoundException {
        List<QuestionTest> questionTests = questionTestRepository.findByTestId(testId);
        if(questionTests.isEmpty()){
            return new ArrayList<>();
        }
        List<QuestionTestDTO> questionTestDTOs = new ArrayList<>();
        for(QuestionTest questionTest:questionTests){
            QuestionDTO questionDTO = modelMapper.map(questionTest.getQuestion(),QuestionDTO.class);
            QuestionTestDTO questionTestDTO = modelMapper.map(questionTest,QuestionTestDTO.class);
            List<AnswerDTO> answersDTO = answerService.getAnswers(questionTest.getQuestion().getId());

            questionTestDTO.getQuestion().setAnswers(answersDTO);
            questionTestDTO.setQuestion(questionDTO);
            questionTestDTOs.add(questionTestDTO);
        }
        return questionTestDTOs;
    }
}
