package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ProfessorRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.ProfessorDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents implementation of service interface with Professor entity.
 * T parameter is provided with ProfessorDTO.
 *
 * @author Vuk Manojlovic
 */
@Service
public class ProfessorServiceImpl implements ServiceInterface<ProfessorDTO> {

    /**
     * Reference variable of ProfessorRepository class.
     */
    private ProfessorRepository professorRepository;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }

    @Override
    public List<ProfessorDTO> findAll() {
        return professorRepository.findAll().stream().map(professor->modelMapper.map(professor, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO findById(Object id) throws NotFoundException {
        Optional<Professor> professor = professorRepository.findById((Integer) id);
        ProfessorDTO professorDTO;
        if(professor.isPresent()){
            professorDTO = modelMapper.map(professor.get(),ProfessorDTO.class);
        }
        else{
            throw new NotFoundException("Did not find professor with id: " + id);
        }
        return professorDTO;
    }

    @Override
    public ProfessorDTO save(ProfessorDTO professorDTO) {
        if(professorDTO==null){
            throw new NullPointerException("Professor can't be null");
        }
        Professor professor = professorRepository.save(modelMapper.map(professorDTO,Professor.class));
        return modelMapper.map(professor,ProfessorDTO.class);
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if(!professorRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Did not find Professor with id: " + id);
        }
        professorRepository.deleteById((Integer)id);
    }
}
