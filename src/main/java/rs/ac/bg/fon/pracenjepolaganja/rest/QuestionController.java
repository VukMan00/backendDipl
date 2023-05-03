package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private ServiceInterface questionService;

    @Autowired
    public QuestionController(ServiceInterface questionService){
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public List<Question> findAll(){
        System.out.println(questionService.findAll());
        return questionService.findAll();
    }
}
