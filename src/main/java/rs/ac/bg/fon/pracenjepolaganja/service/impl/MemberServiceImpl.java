package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.RegistrationDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;

@Service
public class MemberServiceImpl {

    private MemberRepository memberRepository;

    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository,StudentRepository studentRepository){
        this.memberRepository = memberRepository;
        this.studentRepository = studentRepository;
    }

    public void registerMember(RegistrationDTO registrationDTO){
        Member member = new Member();
        Student student = new Student();

        if(registrationDTO.getEmail().contains("fon.bg.ac.rs")){
            throw new UsernameNotFoundException("Member can't register with that email format");
        }

        student.setName(registrationDTO.getName());
        student.setLastname(registrationDTO.getLastname());
        student.setBirth(registrationDTO.getBirth());
        student.setEmail(registrationDTO.getEmail());
        student.setIndex(registrationDTO.getIndex());

        String hashPassword = passwordEncoder.encode(registrationDTO.getPassword());
        member.setPassword(hashPassword);
        member.setUsername(registrationDTO.getEmail());
        member.setRole("ROLE_USER");

        Member savedMember = memberRepository.save(member);
        if(savedMember!=null){
            student.setMemberStudent(savedMember);
            studentRepository.save(student);
        }
        else{
            throw new NullPointerException("Member is not saved");
        }
    }
}
