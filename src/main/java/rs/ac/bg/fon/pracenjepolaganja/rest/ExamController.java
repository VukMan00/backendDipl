package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.ExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.ResultExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.ExamServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.StudentServiceImpl;

import java.util.List;

/**
 * Represent the controller that process all client requests for Exam entity
 * and ResulExam association class.
 * Contains endpoints for methods findAll,findById,save,update,deleteById,
 * getResults, saveResultExam, updateResultExam and deleteResultExam.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/exams")
public class ExamController {

    /**
     * Reference variable of ExamServiceImpl class.
     */
    private ExamServiceImpl examService;

    /**
     * Reference variable of StudentServiceImpl class.
     */
    private StudentServiceImpl studentService;

    @Autowired
    public ExamController(ExamServiceImpl examService, StudentServiceImpl studentService){
        this.examService = examService;
        this.studentService = studentService;
    }

    /**
     * Retrieves all exams from the database.
     * All exams are mapped in DTO form.
     *
     * @return list of ExamDTO objects
     */
    @GetMapping
    public List<ExamDTO> findAll(){
        return examService.findAll();
    }

    /**
     * Retrieves one exam from database with given id
     * in form of ResponseEntity class.
     * Exam is mapped in DTO form.
     *
     * @param id of exam that is needed
     * @return object of ResponseEntity class that contains exam in DTO form
     * @throws NotFoundException if exam with given id does not exist in database
     */
    @GetMapping("{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(examService.findById(id));
    }

    /**
     * Saves the exam in the database.
     * Exam that is going to be saved is in DTO form.
     *
     * @param examDTO exam in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved exam in DTO form
     * @throws NotFoundException when test object doesn't exist
     */
    @PostMapping
    public ResponseEntity<ExamDTO> save(@Valid @RequestBody ExamDTO examDTO) throws NotFoundException {
        return new ResponseEntity<>(examService.save(examDTO), HttpStatus.CREATED);
    }

    /**
     * Updates the exam in the database.
     * Exam that is going to be updated is in DTO form.
     * Forwarded exam must have id that references to exam in database
     * which should be updated.
     * If exam don't have id, then exam is going to be saved not updated in database.
     *
     * @param examDTO exam in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated exam in DTO form
     * @throws NotFoundException when test object doesn't exist
     */
    @PutMapping
    public ResponseEntity<ExamDTO> update(@Valid @RequestBody ExamDTO examDTO) throws NotFoundException {
        return new ResponseEntity<>(examService.save(examDTO), HttpStatus.OK);
    }

    /**
     * Deletes the exam from database.
     *
     * @param id id of exam that is going to be deleted
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if exam with given id does not exist in database
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        examService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    /**
     * Retrieves results of exam.
     * Results of exam are in DTO form of ResultExam entity.
     *
     * @param id of exam whose results are needed
     * @return list of ResultExamDTO objects
     * @throws NotFoundException if ResultExam entities with given exam id does not exist in database
     */
    @GetMapping("/{id}/students")
    public List<ResultExamDTO> getResults(@PathVariable Integer id) throws NotFoundException {
        return examService.getResults(id);
    }

    /**
     * Saves result of exam.
     * ResultExam that is going to be saved is in DTO form.
     * Connects one exam with another student.
     *
     * @param resultExamDTO resultExam in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved resultExam in DTO form
     */
    @PostMapping("/results")
    public ResponseEntity<ResultExamDTO> saveResultExam(@Valid @RequestBody ResultExamDTO resultExamDTO){
        return new ResponseEntity<>(examService.saveResultExam(resultExamDTO),HttpStatus.CREATED);
    }

    /**
     * Updates result of exam.
     * ResultExam that is going to be updated is in DTO form.
     *
     * @param resultExamDTO resultExam in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated resultExam in DTO form
     */
    @PutMapping("/results")
    public ResponseEntity<ResultExamDTO> updateResultExam(@Valid @RequestBody ResultExamDTO resultExamDTO){
        return new ResponseEntity<>(examService.saveResultExam(resultExamDTO),HttpStatus.OK);
    }

    /**
     * Deletes result of exam.
     *
     * @param examId id of exam whose result is going to be deleted.
     * @param studentId id of student whose result is going to be deleted.
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if resultExam with given ids does not exist in database
     */
    @DeleteMapping("/{examId}/students/{studentId}")
    public ResponseEntity<String> deleteResultExam(@PathVariable("examId") Integer examId, @PathVariable("studentId") Integer studentId) throws NotFoundException {
        examService.deleteResultExam(studentId,examId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
