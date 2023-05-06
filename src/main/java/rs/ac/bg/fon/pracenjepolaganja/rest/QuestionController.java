package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionTestDTO;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
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
    public ResponseEntity<QuestionDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(questionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> save(@Valid @RequestBody QuestionDTO questionDTO){
        return new ResponseEntity<>(questionService.save(questionDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        questionService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/answers")
    public List<AnswerDTO> getAnswers(@PathVariable("id")Integer id) throws NotFoundException {
        return answerService.getAnswers(id);
    }

    @GetMapping("/{id}/tests")
    public List<QuestionTestDTO> getTests(@PathVariable("id")Integer id) throws NotFoundException {
        return questionService.getTests(id);
    }

    @PostMapping("/tests")
    public ResponseEntity<QuestionTestDTO> saveQuestionTest(@Valid @RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.CREATED);
    }

    @PutMapping("/tests")
    public ResponseEntity<QuestionTestDTO> updateQuestionTest(@Valid @RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}/tests/{testId}")
    public ResponseEntity<String> deleteQuestionTest(@PathVariable("questionId") Integer questionId, @PathVariable("testId") Integer testId) throws NotFoundException {
        testService.deleteQuestionTest(testId,questionId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
