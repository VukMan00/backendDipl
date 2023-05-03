package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ProfessorRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ServiceInterface<Professor> {

    private ProfessorRepository professorRepository;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }

    @Override
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    @Override
    public Professor findById(Integer id) {
        Optional<Professor> professor = professorRepository.findById(id);
        Professor theProfessor = null;
        if(professor.isPresent()){
            theProfessor = professor.get();
        }
        else{
            throw new RuntimeException("Did not find professor with id - " + id);
        }
        return theProfessor;
    }

    @Override
    public Professor save(Professor professor) {
        if(professor==null){
            throw new NullPointerException("Professor can't be null");
        }
        return professorRepository.save(professor);
    }

    @Override
    public void deleteById(Integer id) {
        if(id<0){
            throw new IllegalArgumentException("Id starts from zero");
        }
        professorRepository.deleteById(id);
    }
}
