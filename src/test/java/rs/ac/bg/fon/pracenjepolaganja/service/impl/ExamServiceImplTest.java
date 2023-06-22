package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import rs.ac.bg.fon.pracenjepolaganja.dao.ExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ResultExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.ExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.ResultExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private ResultExamRepository resultExamRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ExamServiceImpl examService;

    private Exam exam;
    private rs.ac.bg.fon.pracenjepolaganja.entity.Test test;
    private Student student;
    private ResultExam resultExam;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(1)
                .name("Vuk")
                .lastname("Manojlovic")
                .index("2019-0048")
                .birth(LocalDate.of(2000,6,21))
                .email("vm20190048@student.fon.bg.ac.rs")
                .build();

        Professor author = Professor.builder()
                .id(1)
                .name("Dusan")
                .lastname("Savic")
                .email("dusan.savic@fon.bg.ac.rs")
                .build();

        test = rs.ac.bg.fon.pracenjepolaganja.entity.Test.builder()
                .id(1)
                .content("Java for beginners")
                .author(author)
                .build();

        exam = Exam.builder()
                .id(1)
                .name("Exam for Java programing language")
                .date(LocalDate.of(2023,6,30))
                .amphitheater("D405")
                .test(test)
                .build();

        resultExam = ResultExam.builder()
                .points(81)
                .grade(9)
                .exam(exam)
                .student(student)
                .build();
    }

    @Test
    void testFindAll() {
        Exam exam2 = Exam.builder()
                .id(2)
                .name("Exam for IT students")
                .date(LocalDate.of(2023,9,25))
                .amphitheater("D404")
                .test(test)
                .build();

        given(examRepository.findAll()).willReturn(List.of(exam,exam2));

        List<ExamDTO> exams = examService.findAll();

        assertThat(exams).isNotNull();
        assertThat(exams.size()).isEqualTo(2);
        verify(examRepository,times(1)).findAll();
    }

    @Test
    void testFindById() throws NotFoundException {
        given(examRepository.findById(exam.getId())).willReturn(Optional.of(exam));

        ExamDTO examDTO = examService.findById(exam.getId());

        assertThat(examDTO).isNotNull();
        verify(examRepository,times(1)).findById(exam.getId());
    }

    @Test
    void testFindByIdNotFound(){
        Integer examId = 1;
        given(examRepository.findById(examId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            examService.findById(examId);
        });

        verify(examRepository,times(1)).findById(exam.getId());
    }

    @Test
    void testSave() {
        given(examRepository.save(exam)).willReturn(exam);

        ExamDTO savedExamDTO = examService.save(modelMapper.map(exam,ExamDTO.class));

        assertThat(savedExamDTO).isNotNull();
        verify(examRepository,times(1)).save(exam);
    }

    @Test
    void testSaveNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            examService.save(null);
        });
    }

    @Test
    void testDeleteById() throws NotFoundException {
        given(examRepository.findById(exam.getId())).willReturn(Optional.ofNullable(exam));
        willDoNothing().given(examRepository).deleteById(exam.getId());

        examService.deleteById(exam.getId());

        verify(examRepository,times(1)).deleteById(exam.getId());
    }

    @Test
    void testDeleteByIdNotFound(){
        Integer examId = 2;
        given(examRepository.findById(examId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            examService.deleteById(examId);
        });

        verify(examRepository,times(1)).findById(examId);
    }

    @Test
    void testGetResults() throws NotFoundException {
        Student student1 = Student.builder()
                .id(2)
                .name("Balsa")
                .lastname("Kretic")
                .index("2019-0067")
                .birth(LocalDate.of(2000,3,17))
                .email("bk20190067@student.fon.bg.ac.rs")
                .build();

        ResultExam resultExam2 = ResultExam.builder()
                .points(74)
                .grade(8)
                .exam(exam)
                .student(student1)
                .build();

        given(resultExamRepository.findByExamId(exam.getId())).willReturn(List.of(resultExam,resultExam2));

        List<ResultExamDTO> results = examService.getResults(exam.getId());

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(0).getExam().getId()).isEqualTo(exam.getId());
        assertThat(results.get(1).getExam().getId()).isEqualTo(exam.getId());

        verify(resultExamRepository,times(1)).findByExamId(exam.getId());
    }

    @Test
    void testGetResultsExamNotFound(){
        Integer examId = 2;
        given(resultExamRepository.findByExamId(examId)).willReturn(new ArrayList<>());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            examService.getResults(examId);
        });

        verify(resultExamRepository,times(1)).findByExamId(examId);
    }

    @Test
    void testSaveResultExam() {
        given(resultExamRepository.save(resultExam)).willReturn(resultExam);

        ResultExamDTO savedResultExamDTO = examService.saveResultExam(modelMapper.map(resultExam,ResultExamDTO.class));

        assertThat(savedResultExamDTO).isNotNull();
        verify(resultExamRepository,times(1)).save(resultExam);
    }

    @Test
    void testSaveResultExamNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            examService.saveResultExam(null);
        });
    }

    @Test
    void testDeleteResultExam() throws NotFoundException {
        ResultExamPK resultExamPK = new ResultExamPK(exam.getId(),student.getId());
        given(resultExamRepository.findById(resultExamPK)).willReturn(Optional.ofNullable(resultExam));
        willDoNothing().given(resultExamRepository).deleteById(resultExamPK);

        examService.deleteResultExam(student.getId(),exam.getId());

        verify(resultExamRepository,times(1)).deleteById(resultExamPK);
    }

    @Test
    void testDeleteResultExamNotFound(){
        ResultExamPK resultExamPK = new ResultExamPK(2,1);
        given(resultExamRepository.findById(resultExamPK)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            examService.deleteResultExam(1,2);
        });

        verify(resultExamRepository,times(1)).findById(resultExamPK);
    }
}