package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ServiceInterface<ExamDTO> {

    private ExamRepository examRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository){
        this.examRepository = examRepository;
    }

    @Override
    public List<ExamDTO> findAll() {
        List<Exam> exams = examRepository.findAll();
        List<ExamDTO> examsDTO = new ArrayList<>();
        for(Exam exam:exams){
            TestDTO testDTO = modelMapper.map(exam.getTest(),TestDTO.class);
            ExamDTO examDTO = modelMapper.map(exam,ExamDTO.class);
            examDTO.setTest(testDTO);
            examsDTO.add(examDTO);
        }
        return examsDTO;
    }

    @Override
    public ExamDTO findById(Object id) {
        Optional<Exam> exam = examRepository.findById((Integer) id);

        TestDTO testDTO = null;
        Exam theExam = null;
        if(exam.isPresent()){
            theExam = exam.get();
            testDTO = modelMapper.map(theExam.getTest(),TestDTO.class);
        }
        else{
            throw new RuntimeException("Did not find Exam with id - " + (Integer)id);
        }

        ExamDTO examDTO = modelMapper.map(theExam,ExamDTO.class);
        examDTO.setTest(testDTO);
        return examDTO;
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) {
        if(examDTO==null){
            throw new NullPointerException("Exam can't be null");
        }
        Exam exam = modelMapper.map(examDTO,Exam.class);
        return modelMapper.map(examRepository.save(exam),ExamDTO.class);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        examRepository.deleteById(id);
    }
}
