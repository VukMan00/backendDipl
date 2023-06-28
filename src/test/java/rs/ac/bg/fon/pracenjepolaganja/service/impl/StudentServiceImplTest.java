package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ResultExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.ResultExamDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.StudentDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.*;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.security.auth.AuthenticationService;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ResultExamRepository resultExamRepository;

    @Mock
    private MemberRepository memberRepository;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private Exam exam;
    private ResultExam resultExam;
    private Member member;
    private rs.ac.bg.fon.pracenjepolaganja.entity.Test test;

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

        member = Member.builder()
                .username(student.getEmail())
                .password(passwordEncoder.encode(student.getIndex()))
                .role(Role.ROLE_USER)
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
        Student student2 = Student.builder()
                .id(2)
                .name("Dimitrije")
                .lastname("Jovanovic")
                .index("2019-0025")
                .birth(LocalDate.of(2000,7,27))
                .email("dj20190025@student.fon.bg.ac.rs")
                .build();

        given(studentRepository.findAll()).willReturn(List.of(student,student2));

        List<StudentDTO> students = studentService.findAll();

        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
        verify(studentRepository,times(1)).findAll();
    }

    @Test
    void testFindById() throws NotFoundException {
        given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));

        StudentDTO studentDTO = studentService.findById(student.getId());

        assertThat(studentDTO).isNotNull();
        verify(studentRepository,times(1)).findById(student.getId());
    }

    @Test
    void testFindByIdNotFound(){
        Integer studentId = 2;
        given(studentRepository.findById(studentId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            studentService.findById(studentId);
        });

        verify(studentRepository,times(1)).findById(studentId);
    }

    @Test
    void testSave() {
        Member savedMember = Member.builder()
                .username(student.getEmail())
                .password(passwordEncoder.encode(student.getIndex()))
                .role(Role.ROLE_USER)
                .build();

        Student savedStudent = Student.builder()
                .id(1)
                .name("Vuk")
                .lastname("Manojlovic")
                .index("2019-0048")
                .birth(LocalDate.of(2000,6,21))
                .email("vm20190048@student.fon.bg.ac.rs")
                .memberStudent(savedMember)
                .build();
        given(authenticationService.validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Email is valid"));
        given(memberRepository.save(member)).willReturn(member);
        given(studentRepository.save(savedStudent)).willReturn(savedStudent);

        StudentDTO savedStudentDTO = studentService.save(modelMapper.map(student,StudentDTO.class));

        //Test return objects
        assertThat(savedStudentDTO).isNotNull();
        assertThat(savedStudentDTO.getMember()).isNotNull();
        verify(studentRepository,times(1)).save(savedStudent);
        verify(memberRepository,times(1)).save(member);
        verify(authenticationService,times(1)).validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname());
    }

    @Test
    void testSaveNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            studentService.save(null);
        });
    }

    @Test
    void testSaveInvalidEmail(){
        student.setEmail("vukman619@gmail.com");
        given(authenticationService.validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Member can't register with given email. We need your faculty email!"));

        org.junit.jupiter.api.Assertions.assertThrows(BadCredentialsException.class, () -> {
            studentService.save(modelMapper.map(student,StudentDTO.class));
        });

        verify(authenticationService,times(1)).validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname());
    }

    @Test
    void testSaveEmailExists(){
        given(authenticationService.validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Member with given username already exists"));
        org.junit.jupiter.api.Assertions.assertThrows(BadCredentialsException.class, () -> {
            studentService.save(modelMapper.map(student,StudentDTO.class));
        });

        verify(authenticationService,times(1)).validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname());
    }

    @Test
    void testUpdate() throws NotFoundException {
        student.setMemberStudent(member);

        Student student2 = student;
        student2.setEmail("vm20200048@student.fon.bg.ac.rs");
        student2.setIndex("2020-0048");

        Member member2 = member;
        member2.setUsername(student2.getEmail());

        student2.setMemberStudent(member2);

        given(studentRepository.findById(student.getId())).willReturn(Optional.ofNullable(student));
        given(studentRepository.save(student2)).willReturn(student2);
        given(memberRepository.save(member2)).willReturn(member2);
        given(authenticationService.validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Email is valid"));

        StudentDTO updatedStudentDTO = studentService.update(modelMapper.map(student2,StudentDTO.class));

        assertThat(updatedStudentDTO.getEmail()).isEqualTo(student2.getEmail());
        assertThat(updatedStudentDTO.getIndex()).isEqualTo(student2.getIndex());
        assertThat(updatedStudentDTO.getMember().getUsername()).isEqualTo(member2.getUsername());

        verify(studentRepository,times(1)).findById(student.getId());
        verify(studentRepository,times(1)).save(student2);
        verify(memberRepository,times(1)).save(member2);
        verify(authenticationService,times(1)).validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname());
    }

    @Test
    void testUpdateNull(){
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
            studentService.update(null);
        });
    }

    @Test
    void testUpdateInvalidEmail(){
        student.setEmail("vukman619@gmail.com");
        given(authenticationService.validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Member can't register with given email. We need your faculty email!"));
        org.junit.jupiter.api.Assertions.assertThrows(BadCredentialsException.class, () -> {
            studentService.update(modelMapper.map(student,StudentDTO.class));
        });
        verify(authenticationService,times(1)).validateEmail(student.getEmail(),student.getIndex(),student.getName(),student.getLastname());
    }

    @Test
    void testUpdateNotFound(){
        Integer studentId = 2;
        Student student2 = Student.builder()
                .id(studentId)
                .name("Vuk")
                .lastname("Manojlovic")
                .index("2020-0048")
                .birth(LocalDate.of(2000,6,21))
                .email("vm20200048@student.fon.bg.ac.rs")
                .build();

        given(studentRepository.findById(studentId)).willReturn(Optional.empty());
        given(authenticationService.validateEmail(student2.getEmail(),student2.getIndex(),student2.getName(),student2.getLastname())).willReturn(ResponseEntity.status(HttpStatus.OK).body("Email is valid"));

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            studentService.update(modelMapper.map(student2,StudentDTO.class));
        });

        verify(studentRepository,times(1)).findById(studentId);
    }

    @Test
    void testDeleteById() throws NotFoundException {
        given(studentRepository.findById(student.getId())).willReturn(Optional.ofNullable(student));
        willDoNothing().given(studentRepository).deleteById(student.getId());

        studentService.deleteById(exam.getId());

        verify(studentRepository,times(1)).deleteById(student.getId());
    }

    @Test
    void testDeleteByIdNotFound(){
        Integer studentId = 2;
        given(studentRepository.findById(studentId)).willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            studentService.deleteById(studentId);
        });

        verify(studentRepository,times(1)).findById(studentId);
    }

    @Test
    void testGetResults() throws NotFoundException {
        Exam exam2 = Exam.builder()
                .id(2)
                .name("Exam for Java programing language")
                .date(LocalDate.of(2023,6,30))
                .amphitheater("D405")
                .test(test)
                .build();

        ResultExam resultExam2 = ResultExam.builder()
                .points(74)
                .grade(8)
                .exam(exam2)
                .student(student)
                .build();

        given(resultExamRepository.findByStudentId(student.getId())).willReturn(List.of(resultExam,resultExam2));

        List<ResultExamDTO> results = studentService.getResults(student.getId());

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(0).getStudent().getId()).isEqualTo(student.getId());
        assertThat(results.get(1).getStudent().getId()).isEqualTo(student.getId());

        verify(resultExamRepository,times(1)).findByStudentId(student.getId());
    }

    @Test
    void testGetResultsExamNotFound(){
        Integer studentId = 2;
        given(resultExamRepository.findByStudentId(studentId)).willReturn(new ArrayList<>());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> {
            studentService.getResults(studentId);
        });

        verify(resultExamRepository,times(1)).findByStudentId(studentId);
    }
}