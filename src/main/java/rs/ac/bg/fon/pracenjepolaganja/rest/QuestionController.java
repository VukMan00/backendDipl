package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionTestDTO;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.AnswerServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.QuestionServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.TestServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionServiceImpl questionService;

    private AnswerServiceImpl answerService;

    private TestServiceImpl testService;

    @Autowired
    public QuestionController(QuestionServiceImpl questionService, AnswerServiceImpl answerService, TestServiceImpl testService){
        this.questionService = questionService;
        this.answerService = answerService;
        this.testService = testService;
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

    @GetMapping("/{id}/tests")
    public List<QuestionTestDTO> getTests(@PathVariable("id")Integer id){
        return questionService.getTests(id);
    }

    @PostMapping("/tests")
    public ResponseEntity<QuestionTestDTO> saveQuestionTest(@RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.CREATED);
    }

    @PutMapping("/tests")
    public ResponseEntity<QuestionTestDTO> updateQuestionTest(@RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}/tests/{testId}")
    public ResponseEntity<String> deleteQuestionTest(@PathVariable("questionId") Integer questionId, @PathVariable("testId") Integer testId){
        testService.deleteQuestionTest(testId,questionId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
