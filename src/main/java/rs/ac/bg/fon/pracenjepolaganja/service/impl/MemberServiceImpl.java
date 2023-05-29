package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Set;

@Service
public class MemberServiceImpl {

    private MemberRepository memberRepository;

    private StudentRepository studentRepository;

    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository,StudentRepository studentRepository,AuthorityRepository authorityRepository){
        this.memberRepository = memberRepository;
        this.studentRepository = studentRepository;
        this.authorityRepository = authorityRepository;
    }

    public ResponseEntity<String> registerMember(RegistrationDTO registrationDTO){
        String username = registrationDTO.getEmail();
        if(username.contains("fon.bg.ac.rs")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Member can't register with that email format");
        }
        if(!memberRepository.findByUsername(username).isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Member with given username already exists");
        }
        Member member = new Member();
        Student student = new Student();
        Set<Authority> authorities = new HashSet<>();

        student.setName(registrationDTO.getName());
        student.setLastname(registrationDTO.getLastname());
        student.setBirth(registrationDTO.getBirth());
        student.setEmail(username);
        student.setIndex(registrationDTO.getIndex());

        String hashPassword = passwordEncoder.encode(registrationDTO.getPassword());
        member.setPassword(hashPassword);
        member.setUsername(username);
        Member savedMember = memberRepository.save(member);

        Authority authority = new Authority();
        authority.setMember(member);
        authority.setName("ROLE_USER");
        authorities.add(authorityRepository.save(authority));

        savedMember.setAuthorities(authorities);
        student.setMemberStudent(savedMember);
        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Member is successfully registered");
    }
}
