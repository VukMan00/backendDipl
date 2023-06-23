package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.bg.fon.pracenjepolaganja.dao.AuthorityRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.RegistrationDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Authority;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Spy
    private PasswordEncoder passwordEncoder;

    private Member member;

    private Student student;

    private Authority authority;

    private RegistrationDTO registrationDTO;

    @BeforeEach
    void setUp() {
        registrationDTO = RegistrationDTO.builder()
                .name("Marina")
                .lastname("Manojlovic")
                .email("mm20160001@student.fon.bg.ac.rs")
                .password(passwordEncoder.encode("marina97"))
                .index("2016-0001")
                .birth(LocalDate.of(1997,10,7))
                .build();

        member = Member.builder()
                .username(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .authorities(new HashSet<>())
                .build();

        student = Student.builder()
                .name(registrationDTO.getName())
                .lastname(registrationDTO.getLastname())
                .birth(registrationDTO.getBirth())
                .email(registrationDTO.getEmail())
                .index(registrationDTO.getIndex())
                .memberStudent(member)
                .build();

        authority = Authority.builder()
                .name("ROLE_USER")
                .member(member)
                .build();
    }

    @Test
    void testRegisterMember() {
        given(memberRepository.findByUsername(registrationDTO.getEmail())).willReturn(new ArrayList<>());
        given(memberRepository.save(member)).willReturn(member);
        given(authorityRepository.save(authority)).willReturn(authority);
        given(studentRepository.save(student)).willReturn(student);

        ResponseEntity<String> message = memberService.registerMember(registrationDTO);

        assertThat(message).isNotNull();
        assertThat(message.getBody()).isEqualTo("Member is successfully registered");
        verify(memberRepository,times(1)).findByUsername(registrationDTO.getEmail());
        verify(memberRepository,times(1)).save(member);
        verify(authorityRepository,times(1)).save(authority);
        verify(studentRepository,times(1)).save(student);
    }

    @Test
    void testRegisterMemberInvalidEmail(){
        String username = "vukman619@gmail.com";
        registrationDTO.setEmail(username);

        ResponseEntity<String> message = memberService.registerMember(registrationDTO);

        assertThat(message).isNotNull();
        assertThat(message.getBody()).isEqualTo("Member can't register with given email. We need your faculty email!");
    }

    @Test
    void testRegisterMemberEmailExist(){
        List<Member> members = new ArrayList<>();
        members.add(member);
        given(memberRepository.findByUsername(registrationDTO.getEmail())).willReturn(members);

        ResponseEntity<String> message = memberService.registerMember(registrationDTO);

        assertThat(message).isNotNull();
        assertThat(message.getBody()).isEqualTo("Member with given username already exists");
        verify(memberRepository,times(1)).findByUsername(registrationDTO.getEmail());
    }
}