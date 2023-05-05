package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.ExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private ServiceInterface examService;

    @Autowired
    public ExamController(@Qualifier("examServiceImpl") ServiceInterface examService){
        this.examService = examService;
    }

    @GetMapping
    public List<ExamDTO> findAll(){
        return examService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body((ExamDTO)examService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExamDTO> save(@RequestBody ExamDTO examDTO){
        return new ResponseEntity<ExamDTO>((ExamDTO)examService.save(examDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        examService.deleteById(id);
        return new ResponseEntity<String>("Deleted",HttpStatus.OK);
    }
}
