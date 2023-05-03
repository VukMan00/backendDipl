package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements ServiceInterface<Question> {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        Question theQuestion = null;

        if(question.isPresent()){
            theQuestion = question.get();
        }
        else{
            throw new RuntimeException("Did not find Question with id - " + id);
        }
        return theQuestion;
    }

    @Override
    public Question save(Question question) {
        if(question == null){
            throw new NullPointerException("Question can't be null");
        }
        return questionRepository.save(question);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        questionRepository.deleteById(id);
    }
}
