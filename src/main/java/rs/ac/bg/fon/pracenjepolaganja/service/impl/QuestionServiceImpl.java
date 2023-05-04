package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.QuestionRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements ServiceInterface<QuestionDTO> {

    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuestionDTO> findAll() {
        return questionRepository.findAll().stream().map(question->modelMapper.map(question,QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuestionDTO findById(Object id) {
        Optional<Question> question = questionRepository.findById((Integer) id);
        Question theQuestion = null;

        if(question.isPresent()){
            theQuestion = question.get();
        }
        else {
            throw new RuntimeException("Did not find Question with id - " + (Integer)id);
        }
        return modelMapper.map(theQuestion,QuestionDTO.class);
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
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        questionRepository.deleteById(id);
    }
}
