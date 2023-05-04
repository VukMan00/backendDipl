package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.StudentDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.TestDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    private ServiceInterface testService;

    @Autowired
    public TestController(@Qualifier("testServiceImpl") ServiceInterface testService){
        this.testService = testService;
    }

    @GetMapping
    public List<TestDTO> findAll(){
        return testService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<TestDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body((TestDTO) testService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TestDTO> save(@RequestBody TestDTO testDTO){
        return new ResponseEntity<TestDTO>((TestDTO)testService.save(testDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        testService.deleteById(id);
        return new ResponseEntity<String>("Deleted",HttpStatus.OK);
    }
}
