package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.TestRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements ServiceInterface<Test> {

    private TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public Test findById(Integer id) {
        Optional<Test> test = testRepository.findById(id);

        Test theTest = null;
        if(test.isPresent()){
            theTest = test.get();
        }
        else{
            throw new RuntimeException("Did not find Test with id - " + id);
        }
        return theTest;
    }

    @Override
    public Test save(Test test) {
        if(test==null){
            throw new NullPointerException("Test can't be null");
        }
        return testRepository.save(test);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        testRepository.deleteById(id);
    }
}
