package rs.ac.bg.fon.pracenjepolaganja.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
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
    public List<Test> findAll(){
        return testService.findAll();
    }

    @GetMapping("{id}")
    public Test findById(@PathVariable Integer id){
        return (Test) testService.findById(id);
    }

    @PostMapping
    public Test save(@RequestBody Test test){
        return (Test) testService.save(test);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        testService.deleteById(id);
    }
}
