package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ResultExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ServiceInterface<ExamDTO> {

    private ExamRepository examRepository;

    private ResultExamRepository resultExamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository,ResultExamRepository resultExamRepository){
        this.examRepository = examRepository;
        this.resultExamRepository = resultExamRepository;
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

    public List<ResultExamDTO> getResults(Integer id) {
        List<ResultExam> resultsExam = resultExamRepository.findByExamId(id);
        List<ResultExamDTO> resultsExamDTO = new ArrayList<>();

        for(ResultExam resultExam:resultsExam){
            StudentDTO studentDTO = modelMapper.map(resultExam.getStudent(),StudentDTO.class);
            ExamDTO examDTO = modelMapper.map(resultExam.getExam(),ExamDTO.class);
            TestDTO testDTO = modelMapper.map(resultExam.getExam().getTest(),TestDTO.class);
            examDTO.setTest(testDTO);

            ResultExamDTO resultExamDTO = modelMapper.map(resultExam,ResultExamDTO.class);
            resultExamDTO.setExam(examDTO);
            resultExamDTO.setStudent(studentDTO);

            resultsExamDTO.add(resultExamDTO);
        }

        return resultsExamDTO;
    }


    public ResultExamDTO saveResultExam(ResultExamDTO resultExamDTO) {
        if(resultExamDTO == null){
            throw new NullPointerException("ResultExam can't be null");
        }
        ResultExam resultExam = resultExamRepository.save(modelMapper.map(resultExamDTO,ResultExam.class));
        return modelMapper.map(resultExam,ResultExamDTO.class);
    }

    public void deleteResultExam(Integer studentId, Integer examId) {
        if(examId<0 || studentId<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        resultExamRepository.deleteById(new ResultExamPK(examId,studentId));
    }
}
