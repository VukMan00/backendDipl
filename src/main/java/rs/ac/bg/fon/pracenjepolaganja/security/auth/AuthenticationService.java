package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.entity.Role;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.security.config.JwtService;

import java.util.Locale;

/**
 * Service for authentication endpoints.
 * Contains register and authenticate methods
 *
 * @author Vuk Manojlovic
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    /**
     * Reference variable of MemberRepository
     */
    private final MemberRepository memberRepository;

    /**
     * Reference variable of StudentRepository
     */
    private final StudentRepository studentRepository;

    /**
     * Reference variable of PasswordEncoder
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Reference variable of JwtService
     */
    private final JwtService jwtService;

    /**
     * Reference variable of AuthenticationManager
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Registration of member.
     * Provides validation of email.
     *
     * @param request registration object of member
     * @return object of AuthenticationResponse
     */
    public AuthenticationResponse register(RegisterRequest request) {
        ResponseEntity<String> message = validateEmail(request.getEmail(),request.getIndex(),request.getFirstname(),request.getLastname());

        if(!message.getBody().equals("Email is valid")) {
            return AuthenticationResponse.builder()
                    .message(message)
                    .token(null)
                    .build();
        }
        var member = Member.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        Member savedMember = memberRepository.save(member);

        var student = Student.builder()
                .name(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .index(request.getIndex())
                .birth(request.getBirth())
                .memberStudent(savedMember)
                .build();
        studentRepository.save(student);

        var jwtToken = jwtService.generateToken(member);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message(null)
                .build();
    }

    /**
     * Provides authentication for members.
     *
     * @param request request object for authentication
     * @return AuthenticationResponse object of AuthenticationResponse
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        var member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(member);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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
    public ResponseEntity<String> validateEmail(String email, String index, String firstName, String lastName) {
        String initials = (firstName.charAt(0) + "" + lastName.charAt(0)).toLowerCase(Locale.ROOT);
        String[] splitIndex = index.split("-");
        String year = splitIndex[0];
        String number = splitIndex[1];
        if(!email.equals(initials+""+year+""+number+"@student.fon.bg.ac.rs")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Member can't register with given email. We need your faculty email!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Email is valid");
    }
}