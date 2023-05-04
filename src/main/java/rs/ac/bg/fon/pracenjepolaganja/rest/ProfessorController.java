package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    private ServiceInterface professorService;

    @Autowired
    public ProfessorController(@Qualifier("professorServiceImpl") ServiceInterface professorService){
        this.professorService = professorService;
    }

    @GetMapping
    public List<Professor> findAll(){
        return professorService.findAll();
    }

    @GetMapping("{id}")
    public Professor findById(@PathVariable Integer id){
        return (Professor) professorService.findById(id);
    }

    @PostMapping
    public Professor save(@RequestBody Professor professor){
        return (Professor) professorService.save(professor);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        professorService.deleteById(id);
    }
}
