package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.AnswerRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements ServiceInterface<Answer> {

    private AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Answer findById(Integer id) {
        AnswerPK answerPK = new AnswerPK();
        answerPK.setId(id);
        Optional<Answer> answer = answerRepository.findById(answerPK);

        Answer theAnswer = null;

        if(answer.isPresent()){
            theAnswer = answer.get();
        }
        else{
            throw new RuntimeException("Did not find Answer with id - " + id);
        }
        return theAnswer;
    }

    @Override
    public Answer save(Answer answer) {
        if(answer==null){
            throw new NullPointerException("Answer can't be null");
        }
        return answerRepository.save(answer);
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
}
