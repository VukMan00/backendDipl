package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.AuthorityRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.AuthorityDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.MemberDTO;
import rs.ac.bg.fon.pracenjepolaganja.dto.RegistrationDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Authority;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.util.*;

/**
 * Represents service implementation of endpoints for MemberController.
 * Contains implementation of methods registerMember, getUserDetailsAfterLogIn.
 *
 * @author Vuk Manojlovic
 */
@Service
public class MemberServiceImpl {

    /**
     * Reference variable of MemberRepository class.
     */
    private MemberRepository memberRepository;

    /**
     * Reference variable of StudentRepository class.
     */
    private StudentRepository studentRepository;

    /**
     * Reference variable of AuthorityRepository class.
     */
    private AuthorityRepository authorityRepository;

    /**
     * Reference to PasswordEncoder.
     * PasswordEncoder is used for encryption of passwords.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository,StudentRepository studentRepository,AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.studentRepository = studentRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registration of member.
     * Member can't have username of emails other than their student(faculty) email. Also,
     * student must have 'student' in username that represent User status of his account.
     *
     * @param registrationDTO object of registration that contains student details.
     * @return String message of successful(or not successful) registration.
     */
    public ResponseEntity<String> registerMember(RegistrationDTO registrationDTO){
        String username = registrationDTO.getEmail();
        ResponseEntity<String> message = validateEmail(username,registrationDTO.getIndex(),registrationDTO.getName(),registrationDTO.getLastname());
        if(!message.getBody().equals("Email is valid")){
            return message;
        }
        if(!memberRepository.findByUsername(username).isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Member with given username already exists");
        }
        Set<Authority> authorities = new HashSet<>();
        Student student = Student.builder()
                .name(registrationDTO.getName())
                .lastname(registrationDTO.getLastname())
                .birth(registrationDTO.getBirth())
                .email(registrationDTO.getEmail())
                .index(registrationDTO.getIndex())
                .build();

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .authorities(authorities)
                .build();

        Member savedMember = memberRepository.save(member);

        Authority authority = Authority.builder()
                .name("ROLE_USER")
                .member(savedMember)
                .build();

        authorities.add(authorityRepository.save(authority));
        savedMember.setAuthorities(authorities);
        student.setMemberStudent(savedMember);
        studentRepository.save(student);

        return ResponseEntity.status(HttpStatus.CREATED).body("Member is successfully registered");
    }

    /**
     * Provides validation of email that user put.
     * Email must be in form of student faculty account.
     * First two characters must be initial of users name and lastname.
     * Also, must contain number of index in form of yearNumber and with that
     * type of email must be @student.fon.bg.ac.rs.
     * If email exist in database, validation of Email is not successful.
     *
     * @param email email of registration object (student)
     * @param index index of student
     * @param firstName firstname of Student
     * @param lastName lastname of Student
     * @return ResponseEntity message if validation is successful or not.
     */
    public ResponseEntity<String> validateEmail(String email,String index,String firstName,String lastName) {
        String initials = (firstName.charAt(0) + "" + lastName.charAt(0)).toLowerCase(Locale.ROOT);
        String[] splitIndex = index.split("-");
        String year = splitIndex[0];
        String number = splitIndex[1];
        if(!email.equals(initials+""+year+""+number+"@student.fon.bg.ac.rs")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Member can't register with given email. We need your faculty email!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Email is valid");
    }

    /**
     * Returns details of log in member.
     *
     * @param authentication authentication object of member
     * @return Member details of member.
     */
    public MemberDTO getUserDetailsAfterLogin(Authentication authentication) {
        List<Member> members = memberRepository.findByUsername(authentication.getName());
        if(members.get(0)==null){
            return null;
        }
        Optional<Authority> authority = members.get(0).getAuthorities().stream().findFirst();
        MemberDTO memberDTO = modelMapper.map(members.get(0),MemberDTO.class);

        if(authority.isPresent()){
            AuthorityDTO authorityDTO = modelMapper.map(authority.get(),AuthorityDTO.class);
            Set<AuthorityDTO> authoritiesDTO = new HashSet<>();
            authoritiesDTO.add(authorityDTO);
            memberDTO.setAuthorities(authoritiesDTO);
        }

        return memberDTO;
    }

    /**
     * Provides changes to the password of the member.
     *
     * @param id of member whose password is going to change
     * @param memberDTO object with contains new password
     * @return ResponseEntity object with message
     * @throws NotFoundException when member with given id doesn't exist in database
     * @throws NullPointerException if given object is null
     */
    public ResponseEntity<String> changePassword(Integer id, MemberDTO memberDTO) throws NotFoundException {
        if(memberDTO==null){
            throw new NullPointerException("MemberDTO object can't be null");
        }
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()){
            Member dbMember = member.get();
            String hashPassword = passwordEncoder.encode(memberDTO.getPassword());
            System.out.println(hashPassword);

            dbMember.setPassword(hashPassword);
            memberRepository.save(dbMember);

            return ResponseEntity.status(HttpStatus.OK).body("Member has successfully changed the password");
        }
        else{
            throw new NotFoundException("Did not find member with this id: " + id);
        }
    }
}
