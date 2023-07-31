package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ProfessorRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.StudentRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;
import rs.ac.bg.fon.pracenjepolaganja.entity.Role;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.security.config.JwtService;
import rs.ac.bg.fon.pracenjepolaganja.security.token.Token;
import rs.ac.bg.fon.pracenjepolaganja.security.token.TokenRepository;
import rs.ac.bg.fon.pracenjepolaganja.security.token.TokenType;

import java.io.IOException;
import java.util.Optional;

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
     * Reference variable of ProfessorRepository
     */
    private final ProfessorRepository professorRepository;

    /**
     * Reference variable of TokenRepository
     */
    private final TokenRepository tokenRepository;

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
     * @param registrationRequest registration object of member
     * @return object of AuthenticationResponse
     */
    public AuthenticationResponse registration(RegistrationRequest registrationRequest) {
        Optional<Token> dbRegistrationToken = tokenRepository.findByToken(registrationRequest.getRegistrationToken());
        if(dbRegistrationToken.isPresent()){
            tokenRepository.deleteById(dbRegistrationToken.get().getId());
        }
        else{
            return AuthenticationResponse.builder()
                    .message("Greska pri registraciji")
                    .build();
        }

        var member = Member.builder()
                .username(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        var jwtToken = jwtService.generateToken(member);
        var refreshToken = jwtService.generateRefreshToken(member);

        Member savedMember = memberRepository.save(member);
        saveMemberToken(savedMember,jwtToken);

        var student = Student.builder()
                .name(registrationRequest.getFirstname())
                .lastname(registrationRequest.getLastname())
                .email(registrationRequest.getEmail())
                .index(registrationRequest.getIndex())
                .birth(registrationRequest.getBirth())
                .memberStudent(savedMember)
                .build();

        studentRepository.save(student);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(student.getName())
                .lastname(student.getLastname())
                .email(student.getEmail())
                .index(student.getIndex())
                .message("Student je uspesno registrovan")
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
                .orElseThrow(()->new BadCredentialsException("Neispravno uneti podaci"));

        var jwtToken = jwtService.generateToken(member);
        var refreshToken = jwtService.generateRefreshToken(member);
        revokeAllMemberTokens(member);
        saveMemberToken(member,jwtToken);

        Integer id;
        String firstname;
        String lastname;
        String email;
        String index=null;
        String role;

        if(!member.getUsername().contains("@fon.bg.ac.rs")){
            Student student = studentRepository.findByEmail(member.getUsername());
            id = student.getId();
            firstname = student.getName();
            lastname = student.getLastname();
            email = student.getEmail();
            index = student.getIndex();
            role=Role.ROLE_USER.name();
        }
        else{
            Professor professor = professorRepository.findByEmail(member.getUsername());
            id = professor.getId();
            firstname = professor.getName();
            lastname = professor.getLastname();
            email = professor.getEmail();
            role = Role.ROLE_ADMIN.name();
        }

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .index(index)
                .role(role)
                .message("Uspesno ste se ulogovali")
                .build();
    }

    /**
     * Changes the password of member.
     *
     * @param request object of request whose got username and new password.
     * @return ResponseEntity object with message if operation is successful.
     * @throws NotFoundException if member with given username doesn't exist
     */
    public ResponseEntity<String> changePassword(RequestChangePassword request) throws Exception {
        Optional<Member> dbMember = memberRepository.findByUsername(request.getUsername());
        if(dbMember.isPresent()){
            Member member = dbMember.get();

            if(request.getOldPassword()!=null) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getOldPassword()));
            }

            member.setPassword(passwordEncoder.encode(request.getNewPassword()));
            memberRepository.save(member);
        }
        else{
            throw new NotFoundException("Did not find member with username: " + request.getUsername());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Member has successfully changed password");
    }

    /**
     * Refreshes token, generate new access token.
     *
     * @param request object whose contains Bearer token
     * @param response object of response
     * @throws IOException when generating is not valid
     */
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String memberEmail;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        memberEmail = jwtService.extractUsername(refreshToken);
        if(memberEmail!=null){
            var member = this.memberRepository.findByUsername(memberEmail)
                    .orElseThrow();
            if(jwtService.isTokenValid(refreshToken,member)){
                var accessToken = jwtService.generateToken(member);
                revokeAllMemberTokens(member);
                saveMemberToken(member,accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }

    /**
     * Revoke all tokens of member that is valid.
     *
     * @param member member of all revoked tokens
     */
    public void revokeAllMemberTokens(Member member) {
        var validUserTokens = tokenRepository.findAllValidTokenByMember(member.getId());
        System.out.println(validUserTokens);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Saves member token.
     *
     * @param member member whose token is going to be saved
     * @param jwtToken access token whose going to be saved
     */
    public void saveMemberToken(Member member, String jwtToken) {
        var token = Token.builder()
                .member(member)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
