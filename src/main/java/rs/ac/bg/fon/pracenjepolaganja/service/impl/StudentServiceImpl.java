package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ResultExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.Role;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.security.auth.AuthenticationService;
import rs.ac.bg.fon.pracenjepolaganja.security.token.TokenRepository;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents implementation of service interface with Student entity.
 * T parameter is provided with StudentDTO.
 *
 * @author Vuk Manojlovic
 */
@Service
public class StudentServiceImpl implements ServiceInterface<StudentDTO> {

    /**
     * Reference variable of StudentRepository class.
     */
    private StudentRepository studentRepository;

    /**
     * Reference variable of ResultExamRepository class.
     */
    private ResultExamRepository resultExamRepository;

    /**
     * Reference variable of MemberRepository class.
     */
    private MemberRepository memberRepository;

    /**
     * Reference variable of TokenRepository class.
     */
    private TokenRepository tokenRepository;

    /**
     * Reference variable of AuthenticationService class
     */
    private AuthenticationService authenticationService;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    /**
     * Hashing of password for student
     */
    private PasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ResultExamRepository resultExamRepository, MemberRepository memberRepository,
                              AuthenticationService authenticationService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, TokenRepository tokenRepository){
        this.studentRepository = studentRepository;
        this.resultExamRepository = resultExamRepository;
        this.memberRepository = memberRepository;
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
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
    public StudentDTO save(StudentDTO studentDTO) throws Exception {
        try {
            if (studentDTO == null) {
                throw new NullPointerException("Student can't be null");
            }
            if (studentRepository.findByEmail(studentDTO.getEmail()) != null) {
                throw new BadCredentialsException("Member with given email already exists");
            }
            if(studentRepository.findByIndex(studentDTO.getIndex())!=null){
                throw new Exception("There is a student with given index");
            }

            Member member = Member.builder()
                    .username(studentDTO.getEmail())
                    .password(passwordEncoder.encode(studentDTO.getIndex()))
                    .role(Role.ROLE_USER)
                    .build();
            Member savedMember =  memberRepository.save(member);

            Student student = modelMapper.map(studentDTO, Student.class);
            student.setMemberStudent(savedMember);

            Student savedStudent = studentRepository.save(student);

            MemberDTO memberDTO = modelMapper.map(savedStudent.getMemberStudent(), MemberDTO.class);
            studentDTO = modelMapper.map(savedStudent, StudentDTO.class);
            studentDTO.setMember(memberDTO);

            return studentDTO;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Updates student entity.
     * When provided id, save method of studentRepository updates entity with given id, in other cases it saves
     * as new entity in database.
     * EmailDetails of updated Student must be in valid form.
     * Id of updated student must be in database.
     *
     * @param studentDTO Data Transfer Object of updated Student.
     * @return updated student in form of DTO
     * @throws NotFoundException when student with given id doesn't exist in database
     * @throws BadCredentialsException when email of updated student is not in valid form
     */
    public StudentDTO update(StudentDTO studentDTO) throws Exception {
        if(studentDTO==null){
            throw new NullPointerException("Student can't be null");
        }
        Student student = modelMapper.map(studentDTO,Student.class);
        Member member;
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        if(optionalStudent.isPresent()){
            Student dbStudent = optionalStudent.get();
            member = dbStudent.getMemberStudent();
            member.setUsername(student.getEmail());

            Member savedMember = memberRepository.save(member);
            student.setMemberStudent(savedMember);
        }
        else{
            throw new NotFoundException("Did not find Student with id: " + student.getId());
        }

        if(studentDTO.getResults()!=null) {
            Collection<ResultExam> results = studentDTO.getResults().stream().map(resultExamDTO -> modelMapper.map(resultExamDTO, ResultExam.class))
                    .collect(Collectors.toList());
            student.setResultExamCollectionCollection(results);
        }
        return modelMapper.map(studentRepository.save(student),StudentDTO.class);
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        Optional<Student> student = studentRepository.findById((Integer)id);
        if(!student.isPresent()){
            throw new NotFoundException("Did not find Student with id: " + id);
        }
        Optional<Member> member = memberRepository.findByUsername(student.get().getEmail());
        Member dbMember = member.get();

        studentRepository.deleteById((Integer)id);
        memberRepository.deleteById(dbMember.getId());
    }

    /**
     * Retrieves results of exam.
     * Results of exam are in DTO form of ResultExam entity.
     *
     * @param id of student whose results are needed
     * @return list of ResultExamDTO objects
     * @throws NotFoundException if ResultExam entities with given student id does not exist in database.
     */
    public List<ResultExamDTO> getResults(Integer id) throws NotFoundException {
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

    /**
     * Retrieves exams of student.
     *
     * @param studentId id of student whose exams are needed
     * @return list of student exams in DTO form
     * @throws NotFoundException if student with given id doesn't have exams
     */
    public List<ExamDTO> getExams(Integer studentId) throws NotFoundException {
        List<ResultExam> resultsExam = resultExamRepository.findByStudentId(studentId);
        if(resultsExam.isEmpty()){
            throw new NotFoundException("Did not find Exams with studentId: " + studentId);
        }

        List<ExamDTO> exams = new ArrayList<>();
        for(ResultExam resultExam : resultsExam){
            ExamDTO examDTO = modelMapper.map(resultExam.getExam(),ExamDTO.class);
            TestDTO testDTO = modelMapper.map(resultExam.getExam().getTest(),TestDTO.class);
            examDTO.setTest(testDTO);

            exams.add(examDTO);
        }
        return exams;
    }
}
