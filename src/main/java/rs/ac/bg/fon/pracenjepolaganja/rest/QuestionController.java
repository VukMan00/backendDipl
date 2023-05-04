package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
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
    public List<QuestionDTO> findAll(){
        return questionService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body((QuestionDTO)questionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> save(@RequestBody QuestionDTO questionDTO){
        return new ResponseEntity<QuestionDTO>((QuestionDTO)questionService.save(questionDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        questionService.deleteById(id);
        return new ResponseEntity<String>("Deleted",HttpStatus.OK);
    }
}
