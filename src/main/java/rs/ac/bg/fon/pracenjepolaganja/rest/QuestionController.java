package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.AnswerServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.QuestionServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionServiceImpl questionService;

    private AnswerServiceImpl answerService;

    @Autowired
    public QuestionController(QuestionServiceImpl questionService, AnswerServiceImpl answerService){
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping
    public List<QuestionDTO> findAll(){
        return questionService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(questionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> save(@RequestBody QuestionDTO questionDTO){
        return new ResponseEntity<>(questionService.save(questionDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        questionService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/answers")
    public List<AnswerDTO> getAnswers(@PathVariable("id")Integer id){
        return answerService.getAnswers(id);
    }
}
