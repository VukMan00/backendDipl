package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import rs.ac.bg.fon.pracenjepolaganja.dao.ProfessorRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.ProfessorDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceImplTest {

    @Mock
    private ProfessorRepository professorRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ProfessorServiceImpl professorService;

    private Professor professor;

    @BeforeEach
    void setUp() {
        professor = Professor.builder()
                .id(1)
                .name("Dusan")
                .lastname("Savic")
                .email("dusan.savic@fon.bg.ac.rs")
                .build();
    }

    @Test
    void testFindAll() {
        Professor professor1 = Professor.builder()
                .id(2)
                .name("Bojan")
                .lastname("Tomic")
                .email("bojan.tomic@fon.bg.ac.rs")
                .build();

        given(professorRepository.findAll()).willReturn(List.of(professor,professor1));

        List<ProfessorDTO> professors = professorService.findAll();

        assertThat(professors).isNotNull();
        assertThat(professors.size()).isEqualTo(2);
        verify(professorRepository,times(1)).findAll();
    }

    @Test
    void testFindById() throws NotFoundException {
        given(professorRepository.findById(professor.getId())).willReturn(Optional.of(professor));

        ProfessorDTO professorDTO = professorService.findById(professor.getId());

        assertThat(professorDTO).isNotNull();
        verify(professorRepository,times(1)).findById(professor.getId());
    }

    @Test
    void testFindByIdNotFound(){
        Integer professorId = 2;
        given(professorRepository.findById(professorId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            professorService.findById(professorId);
        });

        verify(professorRepository,times(1)).findById(professorId);
    }

    @Test
    void testSave() {
        given(professorRepository.save(professor)).willReturn(professor);

        ProfessorDTO savedProfessorDTO = professorService.save(modelMapper.map(professor,ProfessorDTO.class));

        assertThat(savedProfessorDTO).isNotNull();
        verify(professorRepository,times(1)).save(professor);
    }

    @Test
    void testSaveNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            professorService.save(null);
        });
    }

    @Test
    void testDeleteById() throws NotFoundException {
        given(professorRepository.findById(professor.getId())).willReturn(Optional.ofNullable(professor));
        willDoNothing().given(professorRepository).deleteById(professor.getId());

        professorService.deleteById(professor.getId());

        verify(professorRepository,times(1)).deleteById(professor.getId());
    }

    @Test
    void testDeleteByIdNotFound(){
        Integer professorId = 2;
        given(professorRepository.findById(professorId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            professorService.deleteById(professorId);
        });

        verify(professorRepository,times(1)).findById(professorId);
    }
}