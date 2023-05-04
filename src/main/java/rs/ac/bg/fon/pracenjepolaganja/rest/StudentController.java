package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.StudentDTO;
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
    public List<StudentDTO> findAll(){
        return studentService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body((StudentDTO) studentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> save(@RequestBody StudentDTO studentDTO){
        return new ResponseEntity<StudentDTO>((StudentDTO)studentService.save(studentDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        studentService.deleteById(id);
        return new ResponseEntity<String>("Deleted",HttpStatus.OK);
    }
}
