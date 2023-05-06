package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.ProfessorDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.TestDTO;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.ProfessorServiceImpl;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.TestServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    private ProfessorServiceImpl professorService;

    private TestServiceImpl testService;

    @Autowired
    public ProfessorController(ProfessorServiceImpl professorService, TestServiceImpl testService){
        this.professorService = professorService;
        this.testService = testService;
    }

    @GetMapping
    public List<ProfessorDTO> findAll(){
        return professorService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfessorDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(professorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> save(@Valid @RequestBody ProfessorDTO professorDTO){
        return new ResponseEntity<>(professorService.save(professorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProfessorDTO> update(@Valid @RequestBody ProfessorDTO professorDTO){
        return new ResponseEntity<>(professorService.save(professorDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        professorService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @GetMapping("/{id}/tests")
    public List<TestDTO> getTests(@PathVariable("id") Integer id) throws NotFoundException {
        return testService.getTests(id);
    }
}
