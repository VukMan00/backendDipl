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

/**
 * Represent the controller that process all client requests for Professor entity.
 * Contains endpoints for methods findAll,findById,save,update,deleteById and getTests.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/professors")
public class ProfessorController {

    /**
     * Reference variable of ProfessorServiceImpl class.
     */
    private ProfessorServiceImpl professorService;

    /**
     * Reference variable of TestServiceImpl class.
     */
    private TestServiceImpl testService;

    @Autowired
    public ProfessorController(ProfessorServiceImpl professorService, TestServiceImpl testService){
        this.professorService = professorService;
        this.testService = testService;
    }

    /**
     * Retrieves all professors from the database.
     * All professors are mapped in DTO form.
     *
     * @return list of ProfessorDTO objects
     */
    @GetMapping
    public List<ProfessorDTO> findAll(){
        return professorService.findAll();
    }

    /**
     * Retrieves one answer from database with given id
     * in form of ResponseEntity class.
     * Professor is mapped in DTO form.
     *
     * @param id id of professor that is needed
     * @return object of ResponseEntity class that contains professor in DTO form
     * @throws NotFoundException if professor with given id does not exist in database
     */
    @GetMapping("{id}")
    public ResponseEntity<ProfessorDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(professorService.findById(id));
    }

    /**
     * Saves the professor in the database.
     * Professor that is going to be saved is in DTO form.
     *
     * @param professorDTO professor in DTO form that needs to be saved
     * @return object of ResponseEntity class that contains saved professor in DTO form
     */
    @PostMapping
    public ResponseEntity<ProfessorDTO> save(@Valid @RequestBody ProfessorDTO professorDTO){
        return new ResponseEntity<>(professorService.save(professorDTO), HttpStatus.CREATED);
    }

    /**
     * Updates the professor in the database.
     * Professor that is going to be updated is in DTO form.
     * Forwarded professor must have id that references to professor in database
     * which should be updated.
     * If professor don't have id, then professor is going to be saved not updated in database.
     *
     * @param professorDTO professor in DTO form that needs to be updated
     * @return object of ResponseEntity class that contains updated professor in DTO form
     */
    @PutMapping
    public ResponseEntity<ProfessorDTO> update(@Valid @RequestBody ProfessorDTO professorDTO){
        return new ResponseEntity<>(professorService.save(professorDTO), HttpStatus.OK);
    }

    /**
     * Deletes the professor from database.
     *
     * @param id id of professor that is going to be deleted
     * @return object of ResponseEntity that contains String if deleting is successfully
     * @throws NotFoundException if professor with given id does not exist in database
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NotFoundException {
        professorService.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    /**
     * Retrieves all tests that professor created.
     * All tests are mapped in DTO form.
     *
     * @param id of professor whose test are needed
     * @return list of TestDTO objects
     * @throws NotFoundException if professor with given id doesn't belong to none of tests.
     */
    @GetMapping("/{id}/tests")
    public List<TestDTO> getTests(@PathVariable("id") Integer id) throws NotFoundException {
        return testService.getTests(id);
    }
}
