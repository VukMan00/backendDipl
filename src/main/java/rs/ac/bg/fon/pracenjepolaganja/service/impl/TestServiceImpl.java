package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.TestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.ProfessorDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.TestDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements ServiceInterface<TestDTO> {

    private TestRepository testRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TestServiceImpl(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    @Override
    public List<TestDTO> findAll() {
        List<Test> tests = testRepository.findAll();
        List<TestDTO> testsDTO = new ArrayList<>();
        for(Test test:tests){
            ProfessorDTO professorDTO = modelMapper.map(test.getAuthor(),ProfessorDTO.class);
            TestDTO testDTO = modelMapper.map(test,TestDTO.class);
            testDTO.setProfessor(professorDTO);
            testsDTO.add(testDTO);
        }
        return testsDTO;
    }

    @Override
    public TestDTO findById(Object id) {
        Optional<Test> test = testRepository.findById((Integer) id);

        Test theTest = null;
        TestDTO testDTO = null;
        if(test.isPresent()){
            theTest = test.get();
            ProfessorDTO professorDTO = modelMapper.map(theTest.getAuthor(),ProfessorDTO.class);
            testDTO = modelMapper.map(theTest,TestDTO.class);
            testDTO.setProfessor(professorDTO);
        }
        else{
            throw new RuntimeException("Did not find Test with id - " + (Integer)id);
        }
        return testDTO;
    }

    @Override
    public TestDTO save(TestDTO testDTO) {
        if(testDTO==null){
            throw new NullPointerException("Test can't be null");
        }
        Test test = testRepository.save(modelMapper.map(testDTO,Test.class));
        return modelMapper.map(test,TestDTO.class);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        testRepository.deleteById(id);
    }
}
