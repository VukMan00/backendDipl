package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private ServiceInterface answerService;

    @Autowired
    public AnswerController(@Qualifier("answerServiceImpl") ServiceInterface answerService){
        this.answerService = answerService;
    }

    @GetMapping
    public List<Answer> findAll(){
        return answerService.findAll();
    }

    @GetMapping("{id}")
    public Answer findById(@PathVariable Integer id){
        return (Answer) answerService.findById(id);
    }

    @PostMapping
    public Answer save(@RequestBody Answer answer){
        return (Answer) answerService.save(answer);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        answerService.deleteById(id);
    }

}
