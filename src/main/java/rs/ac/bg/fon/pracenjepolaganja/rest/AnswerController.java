package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
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
    public ResponseEntity<AnswerDTO> findById(@PathVariable("answerId") Integer answerId, @PathVariable("questionId") Integer questionId) throws NotFoundException {
        return ResponseEntity.ok().body(answerService.findById(new AnswerPK(answerId,questionId)));
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> save(@Valid @RequestBody AnswerDTO answerDTO){
        return new ResponseEntity<>(answerService.save(answerDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AnswerDTO> update(@Valid @RequestBody AnswerDTO answerDTO){
        return new ResponseEntity<>(answerService.save(answerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}/question/{questionId}")
    public ResponseEntity<String> deleteById(@PathVariable("answerId") Integer answerId, @PathVariable("questionId")Integer questionId) throws NotFoundException {
        answerService.deleteById(new AnswerPK(answerId,questionId));
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
