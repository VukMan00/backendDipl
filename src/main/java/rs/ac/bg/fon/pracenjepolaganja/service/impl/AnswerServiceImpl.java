package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.AnswerRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements ServiceInterface<AnswerDTO> {

    private AnswerRepository answerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
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
    public AnswerDTO findById(Object answerPK) {
        Optional<Answer> answer = answerRepository.findById((AnswerPK) answerPK);

        Answer theAnswer = null;
        QuestionDTO questionDTO = null;
        if(answer.isPresent()){
            theAnswer = answer.get();
            questionDTO = modelMapper.map(theAnswer.getQuestion(), QuestionDTO.class);
        }
        else{
            throw new RuntimeException("Did not find Answer with id" + (AnswerPK)answerPK);
        }
        AnswerDTO answerDTO = modelMapper.map(theAnswer,AnswerDTO.class);
        answerDTO.setQuestion(questionDTO);
        return answerDTO;
    }

    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {
        if(answerDTO==null){
            throw new NullPointerException("Answer can't be null");
        }

        Answer answer = answerRepository.save(modelMapper.map(answerDTO,Answer.class));
        return modelMapper.map(answer,AnswerDTO.class);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        AnswerPK answerPK = new AnswerPK();
        answerPK.setId(id);
        answerRepository.deleteById(answerPK);
    }

    public List<AnswerDTO> getAnswers(Integer id) {
        return answerRepository.findByQuestionId(id).stream().map(answer->modelMapper.map(answer,AnswerDTO.class))
                .collect(Collectors.toList());
    }
}
