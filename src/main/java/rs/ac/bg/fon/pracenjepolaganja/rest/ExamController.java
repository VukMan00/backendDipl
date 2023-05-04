package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
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
    public List<Exam> findAll(){
        return examService.findAll();
    }

    @GetMapping("{id}")
    public Exam findById(@PathVariable Integer id){
        return (Exam) examService.findById(id);
    }

    @PostMapping
    public Exam save(@RequestBody Exam exam){
        return (Exam) examService.save(exam);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        examService.deleteById(id);
    }
}
