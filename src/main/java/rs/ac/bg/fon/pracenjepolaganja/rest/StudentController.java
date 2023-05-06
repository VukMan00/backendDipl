package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.ResultExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.StudentDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.ExamServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentServiceImpl studentService;

    private ExamServiceImpl examService;

    @Autowired
    public StudentController(StudentServiceImpl studentService, ExamServiceImpl examService){
        this.studentService = studentService;
        this.examService = examService;
    }

    @GetMapping
    public List<StudentDTO> findAll(){
        return studentService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(studentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> save(@RequestBody StudentDTO studentDTO){
        return new ResponseEntity<>(studentService.save(studentDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        studentService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/exams")
    public List<ResultExamDTO> getExams(@PathVariable Integer id) throws NotFoundException {
        return studentService.getExams(id);
    }

    @PostMapping("/results")
    public ResponseEntity<ResultExamDTO> saveResultExam(@RequestBody ResultExamDTO resultExamDTO){
        return new ResponseEntity<>(examService.saveResultExam(resultExamDTO),HttpStatus.CREATED);
    }

    @PutMapping("/results")
    public ResponseEntity<ResultExamDTO> updateResultExam(@RequestBody ResultExamDTO resultExamDTO){
        return new ResponseEntity<>(examService.saveResultExam(resultExamDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}/exams/{examId}")
    public ResponseEntity<String> deleteResultExam(@PathVariable("studentId") Integer studentId, @PathVariable("examId") Integer examId) throws NotFoundException {
        examService.deleteResultExam(studentId,examId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
