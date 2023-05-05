package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.ProfessorDTO;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.ProfessorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    private ProfessorServiceImpl professorService;

    @Autowired
    public ProfessorController(ProfessorServiceImpl professorService){
        this.professorService = professorService;
    }

    @GetMapping
    public List<ProfessorDTO> findAll(){
        return professorService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfessorDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(professorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> save(@RequestBody ProfessorDTO professorDTO){
        return new ResponseEntity<>(professorService.save(professorDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        professorService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
