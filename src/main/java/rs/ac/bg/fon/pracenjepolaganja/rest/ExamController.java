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

@RestController
@RequestMapping("/exams")
public class ExamController {

    private ExamServiceImpl examService;

    private StudentServiceImpl studentService;

    @Autowired
    public ExamController(ExamServiceImpl examService, StudentServiceImpl studentService){
        this.examService = examService;
        this.studentService = studentService;
    }

    @GetMapping
    public List<ExamDTO> findAll(){
        return examService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(examService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExamDTO> save(@Valid @RequestBody ExamDTO examDTO){
        return new ResponseEntity<>(examService.save(examDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ExamDTO> update(@Valid @RequestBody ExamDTO examDTO){
        return new ResponseEntity<>(examService.save(examDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        examService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public List<ResultExamDTO> getResults(@PathVariable Integer id) throws NotFoundException {
        return examService.getResults(id);
    }

    @PostMapping("/results")
    public ResponseEntity<ResultExamDTO> saveResultExam(@Valid @RequestBody ResultExamDTO resultExamDTO){
        return new ResponseEntity<>(examService.saveResultExam(resultExamDTO),HttpStatus.CREATED);
    }

    @PutMapping("/results")
    public ResponseEntity<ResultExamDTO> updateResultExam(@Valid @RequestBody ResultExamDTO resultExamDTO){
        return new ResponseEntity<>(examService.saveResultExam(resultExamDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{examId}/students/{studentId}")
    public ResponseEntity<String> deleteResultExam(@PathVariable("examId") Integer examId, @PathVariable("studentId") Integer studentId) throws NotFoundException {
        examService.deleteResultExam(studentId,examId);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
