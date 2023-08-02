package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.AnswerRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents implementation of service interface with Answer entity.
 * T parameter is provided with AnswerDTO.
 *
 * @author Vuk Manojlovic
 */
@Service
public class AnswerServiceImpl implements ServiceInterface<AnswerDTO> {

    /**
     * Reference variable of AnswerRepository class.
     */
    private AnswerRepository answerRepository;

    /**
     * Reference variable of QuestionRepository class.
     */
    private QuestionRepository questionRepository;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository,QuestionRepository questionRepository, ModelMapper modelMapper){
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<AnswerDTO> findAll() {
        List<Answer> answers = answerRepository.findAll();
        List<AnswerDTO> answersDTO = new ArrayList<>();
        for(Answer answer:answers){
            QuestionDTO questionDTO = modelMapper.map(answer.getQuestion(),QuestionDTO.class);
            AnswerDTO answerDTO = modelMapper.map(answer,AnswerDTO.class);
            answerDTO.setQuestion(questionDTO);
            answersDTO.add(answerDTO);
        }
        return answersDTO;
    }

    @Override
    public AnswerDTO findById(Object id) throws NotFoundException {
        AnswerPK answerPK = (AnswerPK)id;
        Optional<Answer> answer = answerRepository.findById(answerPK);
        AnswerDTO answerDTO;
        if(answer.isPresent()){
            answerDTO = modelMapper.map(answer.get(),AnswerDTO.class);
        }
        else{
            throw new NotFoundException("Odgovor nije pronadjen");
        }
        return answerDTO;
    }

    @Override
    public AnswerDTO save(AnswerDTO answerDTO) throws NotFoundException {
        if (answerDTO == null) {
            throw new NullPointerException("Odgovor ne moze biti null");
        }
        if (questionRepository.findById(answerDTO.getAnswerPK().getQuestionId()).isPresent()){
            Answer answer = modelMapper.map(answerDTO,Answer.class);
            Answer savedAnswer = answerRepository.save(answer);
            return modelMapper.map(savedAnswer, AnswerDTO.class);
        }
        else{
            throw new NotFoundException("Pitanje nije pronadjeno");
        }
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        AnswerPK answerPK = (AnswerPK) id;
        if(!answerRepository.findById(answerPK).isPresent()){
            throw new NotFoundException("Odgovor nije pronadjen");
        }
        answerRepository.deleteById(answerPK);
    }

    /**
     * Retrieves all answers that question has.
     * Method is called from QuestionController.
     *
     * @param id id of question whose answers are needed.
     * @return list of answers in DTO form.
     * @throws NotFoundException if question of the given id does not have answers.
     */
    public List<AnswerDTO> getAnswers(Integer id) throws NotFoundException {
        List<Answer> answers = answerRepository.findByQuestionId(id);
        if(answers.isEmpty()){
            return new ArrayList<>();
        }
        List<AnswerDTO> answersDTO = answers.stream().map(answer->modelMapper.map(answer,AnswerDTO.class))
                .collect(Collectors.toList());
        return answersDTO;
    }
}
