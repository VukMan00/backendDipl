package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.QuestionDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.StudentDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements ServiceInterface<StudentDTO> {

    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(student->modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO findById(Object id) {
        Optional<Student> student = studentRepository.findById((Integer) id);
        Student theStudent = null;
        StudentDTO studentDTO = null;
        if(student.isPresent()){
            theStudent = student.get();
            studentDTO = modelMapper.map(theStudent,StudentDTO.class);
        }
        else{
            throw new RuntimeException("Did not find Student with id - " + (Integer)id);
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
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        studentRepository.deleteById(id);
    }
}
