package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;
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
    public List<AnswerDTO> findAll(){
        return answerService.findAll();
    }

    @GetMapping("/{answerId}/question/{questionId}")
    public ResponseEntity<AnswerDTO> findById(@PathVariable("answerId") Integer answerId,@PathVariable("questionId") Integer questionId){
        return ResponseEntity.ok().body((AnswerDTO)answerService.findById(new AnswerPK(answerId,questionId)));
    }

    @PostMapping
    public ResponseEntity<AnswerDTO> save(@RequestBody AnswerDTO answerDTO){
        return new ResponseEntity<AnswerDTO>((AnswerDTO) answerService.save(answerDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        answerService.deleteById(id);
        return new ResponseEntity<String>("Deleted",HttpStatus.OK);
    }

}
