package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ResultExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements ServiceInterface<StudentDTO> {

    private StudentRepository studentRepository;

    private ResultExamRepository resultExamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,ResultExamRepository resultExamRepository){
        this.studentRepository = studentRepository;
        this.resultExamRepository = resultExamRepository;
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(student->modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO findById(Object id) throws NotFoundException {
        Optional<Student> student = studentRepository.findById((Integer) id);
        StudentDTO studentDTO;
        if(student.isPresent()){
            studentDTO = modelMapper.map(student.get(),StudentDTO.class);
        }
        else{
            throw new NotFoundException("Did not find Student with id: " + id);
        }
        return studentDTO;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        if(studentDTO==null){
            throw new NullPointerException("Student can't be null");
        }
        Student student = studentRepository.save(modelMapper.map(studentDTO,Student.class));
        return modelMapper.map(student,StudentDTO.class);
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if(!studentRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Did not find Test with id: " + id);
        }
        studentRepository.deleteById((Integer)id);
    }

    public List<ResultExamDTO> getExams(Integer id) throws NotFoundException {
        List<ResultExam> resultsExam = resultExamRepository.findByStudentId(id);
        if(resultsExam.isEmpty()){
            throw new NotFoundException("Did not find ResultExam with studentId: " +id);
        }
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
}
