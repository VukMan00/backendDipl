package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private ServiceInterface studentService;

    @Autowired
    public StudentController(@Qualifier("studentServiceImpl") ServiceInterface studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> findAll(){
        return studentService.findAll();
    }

    @GetMapping("{id}")
    public Student findById(@PathVariable Integer id){
        return (Student) studentService.findById(id);
    }

    @PostMapping
    public Student save(@RequestBody Student student){
        return (Student) studentService.save(student);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        studentService.deleteById(id);
    }
}
