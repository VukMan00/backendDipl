package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private ServiceInterface questionService;

    @Autowired
    public QuestionController(@Qualifier("questionServiceImpl") ServiceInterface questionService){
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> findAll(){
        return questionService.findAll();
    }

    @GetMapping("{id}")
    public Question findById(@PathVariable Integer id){
        return (Question) questionService.findById(id);
    }

    @PostMapping
    public Question save(@RequestBody Question question){
        return (Question) questionService.save(question);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        questionService.deleteById(id);
    }
}
