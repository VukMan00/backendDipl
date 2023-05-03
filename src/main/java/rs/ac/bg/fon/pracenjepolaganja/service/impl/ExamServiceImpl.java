package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ServiceInterface<Exam> {

    private ExamRepository examRepository;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository){
        this.examRepository = examRepository;
    }

    @Override
    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam findById(Integer id) {
        Optional<Exam> exam = examRepository.findById(id);

        Exam theExam = null;
        if(exam.isPresent()){
            theExam = exam.get();
        }
        else{
            throw new RuntimeException("Did not find Exam with id - " + id);
        }
        return theExam;
    }

    @Override
    public Exam save(Exam exam) {
        if(exam==null){
            throw new NullPointerException("Exam can't be null");
        }
        return examRepository.save(exam);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        examRepository.deleteById(id);
    }
}
