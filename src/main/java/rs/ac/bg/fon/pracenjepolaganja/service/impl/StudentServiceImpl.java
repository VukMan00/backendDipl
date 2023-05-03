package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements ServiceInterface<Student> {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Integer id) {
        Optional<Student> student = studentRepository.findById(id);

        Student theStudent = null;
        if(student.isPresent()){
            theStudent = student.get();
        }
        else{
            throw new RuntimeException("Did not find Student with id - " + id);
        }
        return theStudent;
    }

    @Override
    public Student save(Student student) {
        if(student==null){
            throw new NullPointerException("Student can't be null");
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        studentRepository.deleteById(id);
    }
}
