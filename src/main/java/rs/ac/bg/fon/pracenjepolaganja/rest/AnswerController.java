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

/**
 * Represent the controller that process all client requests for Answer entity.
 * Contains endpoints for methods findAll,findById,save,update and deleteById.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/answers")
public class AnswerController {

    /**
     * Reference variable of AnswerServiceImpl class.
     */
    private AnswerServiceImpl answerService;

    @Autowired
    public AnswerController(AnswerServiceImpl answerService){
        this.answerService = answerService;
    }

    /**
     * Retrieves all answers from the database.
     * All answers are mapped in DTO form.
     *
     * @return list of AnswerDTO objects
     */
    @GetMapping
    public List<AnswerDTO> findAll(){
        return answerService.findAll();
    }

    /**
     * Retrieves one answer from database with given answer and question id
     * in form of ResponseEntity class.
     * Answer is mapped in DTO form.
     *
     * @param answerId id of answer that is needed
     * @param questionId id of question that answer belongs to
     * @return object of ResponseEntity class that contains answer in DTO form
     * @throws NotFoundException if answer with given ids does not exist in database
     */
    @GetMapping("/{answerId}/question/{questionId}")
    public ResponseEntity<AnswerDTO> findById(@PathVariable("answerId") Integer answerId, @PathVariable("questionId") Integer questionId) throws NotFoundException {
        return ResponseEntity.ok().body(answerService.findById(new AnswerPK(answerId,questionId)));
    }

    /**
     * Saves the answer in the database.
     * Answer that is going to be saved is in DTO form.
     *
     * @param answerDTO answer in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved answer in DTO form
     */
    @PostMapping
    public ResponseEntity<AnswerDTO> save(@Valid @RequestBody AnswerDTO answerDTO){
        return new ResponseEntity<>(answerService.save(answerDTO), HttpStatus.CREATED);
    }

    /**
     * Updates the answer in the database.
     * Answer that is going to be updated is in DTO form.
     * Forwarded answer must have id that references to answer in database
     * which should be updated.
     * If answer don't have id, then answer is going to be saved not updated in database.
     *
     * @param answerDTO answer in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated answer in DTO form
     */
    @PutMapping
    public ResponseEntity<AnswerDTO> update(@Valid @RequestBody AnswerDTO answerDTO){
        return new ResponseEntity<>(answerService.save(answerDTO), HttpStatus.OK);
    }

    /**
     * Deletes the answer from database.
     *
     * @param answerId id of answer that is going to be deleted
     * @param questionId id of question that answer belongs to
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if answer with given ids does not exist in database
     */
    @DeleteMapping("/{answerId}/question/{questionId}")
    public ResponseEntity<String> deleteById(@PathVariable("answerId") Integer answerId, @PathVariable("questionId")Integer questionId) throws NotFoundException {
        answerService.deleteById(new AnswerPK(answerId,questionId));
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
