package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.ExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.ResultExamDTO;
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
    public ResponseEntity<ExamDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(examService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExamDTO> save(@RequestBody ExamDTO examDTO){
        return new ResponseEntity<>(examService.save(examDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        examService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public List<ResultExamDTO> getResults(@PathVariable Integer id){
        return studentService.getResults(id);
    }
}
