package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.AnswerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private AnswerServiceImpl answerService;

    @Autowired
    public AnswerController(AnswerServiceImpl answerService){
        this.answerService = answerService;
    }

    @GetMapping
    public List<AnswerDTO> findAll(){
        return answerService.findAll();
    }

    @GetMapping("/{answerId}/question/{questionId}")
    public ResponseEntity<AnswerDTO> findById(@PathVariable("answerId") Integer answerId,@PathVariable("questionId") Integer questionId){
        return ResponseEntity.ok().body(answerService.findById(new AnswerPK(answerId,questionId)));
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> save(@RequestBody AnswerDTO answerDTO){
        return new ResponseEntity<>(answerService.save(answerDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        answerService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
