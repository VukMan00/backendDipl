package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.ExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.ResultExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.StudentDTO;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.ExamServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.StudentServiceImpl;

import java.util.List;

/**
 * Represent the controller that process all client requests for Student entity
 * and ResulExam association class.
 * Contains endpoints for methods findAll,findById,save,update,deleteById,
 * getResults, saveResultExam, updateResultExam and deleteResultExam.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    /**
     * Reference variable of StudentServiceImpl class.
     */
    private StudentServiceImpl studentService;

    /**
     * Reference variable of ExamServiceImpl class.
     */
    private ExamServiceImpl examService;

    @Autowired
    public StudentController(StudentServiceImpl studentService, ExamServiceImpl examService){
        this.studentService = studentService;
        this.examService = examService;
    }

    /**
     * Retrieves all students from the database.
     * All students are mapped in DTO form.
     *
     * @return list of StudentDTO objects
     */
    @GetMapping
    public List<StudentDTO> findAll(){
        return studentService.findAll();
    }

    /**
     * Retrieves one student from database with given id
     * in form of ResponseEntity class.
     * Student is mapped in DTO form.
     *
     * @param id of student that is needed
     * @return object of ResponseEntity class that contains student in DTO form
     * @throws NotFoundException if student with given id does not exist in database
     */
    @GetMapping("{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(studentService.findById(id));
    }

    /**
     * Saves the student in the database.
     * Student that is going to be saved is in DTO form.
     *
     * @param studentDTO student in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved student in DTO form
     */
    @PostMapping
    public ResponseEntity<StudentDTO> save(@Valid @RequestBody StudentDTO studentDTO) throws Exception {
        return new ResponseEntity<>(studentService.save(studentDTO), HttpStatus.CREATED);
    }

    /**
     * Updates the student in the database.
     * Student that is going to be updated is in DTO form.
     * Forwarded student must have id that references to student in database
     * which should be updated.
     * If student don't have id, then student is going to be saved not updated in database.
     *
     * @param studentDTO student in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated student in DTO form
     */
    @PutMapping
    public ResponseEntity<StudentDTO> update(@Valid @RequestBody StudentDTO studentDTO) throws Exception {
        return new ResponseEntity<>(studentService.update(studentDTO), HttpStatus.OK);
    }

    /**
     * Deletes the student from database.
     *
     * @param id id of student that is going to be deleted
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if student with given id does not exist in database
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        studentService.deleteById(id);
        return new ResponseEntity<>("Student je izbrisan",HttpStatus.OK);
    }

    /**
     * Retrieves exams of student.
     *
     * @param studentId id of student whose exams are needed
     * @return list of student exams
     * @throws NotFoundException if student with given id doesn't have exams
     */
    @GetMapping("/{studentId}/exams")
    public List<ExamDTO> getExamsOfStudent(@PathVariable Integer studentId) throws NotFoundException {
        return studentService.getExams(studentId);
    }

    /**
     * Retrieves results of exam.
     * Results of exam are in DTO form of ResultExam entity.
     *
     * @param id of student whose results are needed
     * @return list of ResultExamDTO objects
     * @throws NotFoundException if ResultExam entities with given student id does not exist in database.
     */
    @GetMapping("/{id}/results")
    public List<ResultExamDTO> getResults(@PathVariable Integer id) throws NotFoundException {
        return studentService.getResults(id);
    }

    /**
     * Saves result of exam.
     * ResultExam that is going to be saved is in DTO form.
     * Connects one student with another exam.
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
     * @param studentId id of student whose result is going to be deleted.
     * @param examId id of exam whose result is going to be deleted.
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if resultExam with given ids does not exist in database
     */
    @DeleteMapping("/{studentId}/exams/{examId}")
    public ResponseEntity<String> deleteResultExam(@PathVariable("studentId") Integer studentId, @PathVariable("examId") Integer examId) throws NotFoundException {
        examService.deleteResultExam(studentId,examId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
