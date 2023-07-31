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

/**
 * Represent the controller that process all client requests for Test entity
 * and QuestionTest association class.
 * Contains endpoints for methods findAll,findById,save,update,deleteById,
 * getQuestions,saveQuestionTest,updateQuestionTest and deleteQuestionTest.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/tests")
public class TestController {

    /**
     * Reference variable of TestServiceImpl class.
     */
    private TestServiceImpl testService;

    /**
     * Reference variable of QuestionServiceImpl class.
     */
    private QuestionServiceImpl questionService;

    @Autowired
    public TestController(TestServiceImpl testService,QuestionServiceImpl questionService){
        this.testService = testService;
        this.questionService = questionService;
    }

    /**
     * Retrieves all tests from the database.
     * All tests are mapped in DTO form.
     *
     * @return list of TestDTO objects
     */
    @GetMapping
    public List<TestDTO> findAll(){
        return testService.findAll();
    }

    /**
     * Retrieves one test from database with given test id
     * in form of ResponseEntity class.
     * Test is mapped in DTO form.
     *
     * @param id id of test that is needed
     * @return object of ResponseEntity class that contains test in DTO form
     * @throws NotFoundException if test with given id does not exist in database
     */
    @GetMapping("{id}")
    public ResponseEntity<TestDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(testService.findById(id));
    }

    /**
     * Saves the test in the database.
     * Test that is going to be saved is in DTO form.
     *
     * @param testDTO test in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved test in DTO form
     * @throws NotFoundException when professor doesn't exist in database
     */
    @PostMapping
    public ResponseEntity<TestDTO> save(@Valid @RequestBody TestDTO testDTO) throws NotFoundException {
        return new ResponseEntity<>(testService.save(testDTO), HttpStatus.CREATED);
    }

    /**
     * Updates the test in the database.
     * Test that is going to be updated is in DTO form.
     * Forwarded test must have id that references to test in database
     * which should be updated.
     * If test don't have id, then test is going to be saved not updated in database.
     *
     * @param testDTO test in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated test in DTO form
     * @throws NotFoundException when professor doesn't exist in database
     */
    @PutMapping
    public ResponseEntity<TestDTO> update(@Valid @RequestBody TestDTO testDTO) throws NotFoundException {
        return new ResponseEntity<>(testService.save(testDTO), HttpStatus.OK);
    }

    /**
     * Deletes the test from database.
     *
     * @param id id of test that is going to be deleted
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if test with given id does not exist in database
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        testService.deleteById(id);
        return new ResponseEntity<>("Test",HttpStatus.OK);
    }

    /**
     * Retrieves all questions that test have and points
     * for each question as list of QuestionTest objects.
     * QuestionTest objects are mapped in DTO form.
     *
     * @param id of test whose questions and points are needed
     * @return list of QuestionTestDTO objects
     * @throws NotFoundException if QuestionTest entities with given test id does not exist in database
     */
    @GetMapping("/{id}/questions")
    public List<QuestionTestDTO> getQuestionsTest(@PathVariable("id") Integer id) throws NotFoundException {
        return questionService.getQuestionsTest(id);
    }

    /**
     * Saves the questionTest entity in database.
     * QuestionTest entity is mapped in DTO form.
     * Connects one test to another question.
     *
     * @param questionTestDTO questionTest in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved questionTest in DTO form
     */
    @PostMapping("/questions")
    public ResponseEntity<QuestionTestDTO> saveQuestionTest(@Valid @RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.CREATED);
    }

    /**
     * Updates points that question has in test.
     * QuestionTest that is going to be updated is in DTO form.
     *
     * @param questionTestDTO questionTest in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated questionTest in DTO form
     */
    @PutMapping("/questions")
    public ResponseEntity<QuestionTestDTO> updateQuestionTest(@Valid @RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.OK);
    }

    /**
     * Deletes question from test, questionTest entity.
     *
     * @param testId id of test whose question is going to be deleted
     * @param questionId id of question that is going to be deleted
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if QuestionTest with given ids does not exist in database
     */
    @DeleteMapping("/{testId}/questions/{questionId}")
    public ResponseEntity<String> deleteQuestionTest(@PathVariable("testId") Integer testId, @PathVariable("questionId") Integer questionId) throws NotFoundException {
        testService.deleteQuestionTest(testId,questionId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
