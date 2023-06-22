package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.AuthorityRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.RegistrationDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Authority;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
        if(!username.toLowerCase().contains("@student.fon.bg.ac.rs")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Member can't register with given email. We need your faculty email!");
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
     * Returns details of log in member.
     *
     * @param authentication authentication object of member
     * @return Member details of member.
     */
    public Member getUserDetailsAfterLogin(Authentication authentication) {
        List<Member> members = memberRepository.findByUsername(authentication.getName());
        if (members.size() > 0) {
            return members.get(0);
        } else {
            return null;
        }

    }
}
