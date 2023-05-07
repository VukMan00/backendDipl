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

/**
 * Represent the controller that process all client requests for Question entity
 * and QuestionTest association class.
 * Contains endpoints for methods findAll,findById,save,update,deleteById,
 * getAnswers,getTests,saveQuestionTest,updateQuestionTest and deleteQuestionTest.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/questions")
public class QuestionController {

    /**
     * Reference variable of QuestionServiceImpl class.
     */
    private QuestionServiceImpl questionService;

    /**
     * Reference variable of AnswerServiceImpl class.
     */
    private AnswerServiceImpl answerService;

    /**
     * Reference variable of TestServiceImpl class.
     */
    private TestServiceImpl testService;

    @Autowired
    public QuestionController(QuestionServiceImpl questionService, AnswerServiceImpl answerService, TestServiceImpl testService){
        this.questionService = questionService;
        this.answerService = answerService;
        this.testService = testService;
    }

    /**
     * Retrieves all questions from the database.
     * All questions are mapped in DTO form.
     *
     * @return list of QuestionDTO objects
     */
    @GetMapping
    public List<QuestionDTO> findAll(){
        return questionService.findAll();
    }

    /**
     * Retrieves one question from database with given question id
     * in form of ResponseEntity class.
     * Question is mapped in DTO form.
     *
     * @param id id of question that is needed
     * @return object of ResponseEntity class that contains question in DTO form
     * @throws NotFoundException if question with given id does not exist in database
     */
    @GetMapping("{id}")
    public ResponseEntity<QuestionDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(questionService.findById(id));
    }

    /**
     * Saves the question in the database.
     * Question that is going to be saved is in DTO form.
     *
     * @param questionDTO question in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved question in DTO form
     */
    @PostMapping
    public ResponseEntity<QuestionDTO> save(@Valid @RequestBody QuestionDTO questionDTO){
        return new ResponseEntity<>(questionService.save(questionDTO),HttpStatus.CREATED);
    }

    /**
     * Updates the question in the database.
     * Question that is going to be updated is in DTO form.
     * Forwarded question must have id that references to question in database
     * which should be updated.
     * If question don't have id, then question is going to be saved not updated in database.
     *
     * @param questionDTO question in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated question in DTO form
     */
    @PutMapping
    public ResponseEntity<QuestionDTO> update(@Valid @RequestBody QuestionDTO questionDTO){
        return new ResponseEntity<>(questionService.save(questionDTO), HttpStatus.OK);
    }

    /**
     * Deletes the question from database.
     *
     * @param id id of question that is going to be deleted
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if question with given id does not exist in database
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        questionService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    /**
     * Retrieves answers from question.
     * Answers are mapped in DTO form.
     *
     * @param id of question whose answers are needed
     * @return list of AnswerDTO objects
     * @throws NotFoundException if question with given id does not have answers
     */
    @GetMapping("/{id}/answers")
    public List<AnswerDTO> getAnswers(@PathVariable("id")Integer id) throws NotFoundException {
        return answerService.getAnswers(id);
    }

    /**
     * Retrieves all tests where question belongs and points
     * that question has in each test as list of QuestionTest objects.
     * QuestionTest objects are mapped in DTO form.
     *
     * @param id of question whose points and tests are needed
     * @return list of QuestionTestDTO objects
     * @throws NotFoundException if QuestionTest entities with given question id does not exist in database.
     */
    @GetMapping("/{id}/tests")
    public List<QuestionTestDTO> getTests(@PathVariable("id")Integer id) throws NotFoundException {
        return testService.getTestsFromQuestion(id);
    }

    /**
     * Saves the questionTest entity in database.
     * QuestionTest entity is mapped in DTO form.
     * Connects one question to another test.
     *
     * @param questionTestDTO questionTest in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved questionTest in DTO form
     */
    @PostMapping("/tests")
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
    @PutMapping("/tests")
    public ResponseEntity<QuestionTestDTO> updateQuestionTest(@Valid @RequestBody QuestionTestDTO questionTestDTO){
        return new ResponseEntity<>(testService.saveQuestionTest(questionTestDTO),HttpStatus.OK);
    }

    /**
     * Deletes question from test, questionTest entity.
     *
     * @param questionId id of question that is going to be deleted.
     * @param testId id of test whose question is going to be deleted.
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if QuestionTest with given ids does not exist in database
     */
    @DeleteMapping("/{questionId}/tests/{testId}")
    public ResponseEntity<String> deleteQuestionTest(@PathVariable("questionId") Integer questionId, @PathVariable("testId") Integer testId) throws NotFoundException {
        testService.deleteQuestionTest(testId,questionId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
