package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionTestDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.TestDTO;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.QuestionServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.TestServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    private TestServiceImpl testService;

    private QuestionServiceImpl questionService;

    @Autowired
    public TestController(TestServiceImpl testService,QuestionServiceImpl questionService){
        this.testService = testService;
        this.questionService = questionService;
    }

    @GetMapping
    public List<TestDTO> findAll(){
        return testService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<TestDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(testService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TestDTO> save(@Valid @RequestBody TestDTO testDTO){
        return new ResponseEntity<>(testService.save(testDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        testService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/questions")
    public List<QuestionTestDTO> getQuestions(@PathVariable("id") Integer id) throws NotFoundException {
        return testService.getQuestions(id);
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionTestDTO> saveQuestionTest(@Valid @RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.CREATED);
    }

    @PutMapping("/questions")
    public ResponseEntity<QuestionTestDTO> updateQuestionTest(@Valid @RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{testId}/questions/{questionId}")
    public ResponseEntity<String> deleteQuestionTest(@PathVariable("testId") Integer testId, @PathVariable("questionId") Integer questionId) throws NotFoundException {
        testService.deleteQuestionTest(testId,questionId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
