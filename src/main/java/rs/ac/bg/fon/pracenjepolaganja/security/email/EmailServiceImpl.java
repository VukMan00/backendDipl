package rs.ac.bg.fon.pracenjepolaganja.security.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.security.auth.AuthenticationService;
import rs.ac.bg.fon.pracenjepolaganja.security.config.JwtService;
import rs.ac.bg.fon.pracenjepolaganja.security.token.Token;
import rs.ac.bg.fon.pracenjepolaganja.security.token.TokenRepository;

import java.util.List;
import java.util.Optional;

/**
 * Represent implementation of EmailService interface.
 *
 * @author Vuk Manojlovic
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    /**
     * Reference variable of JavaMailSender
     */
    private final JavaMailSender mailSender;

    /**
     * Reference variable of JwtService
     */
    private final JwtService jwtService;

    /**
     * Reference variable of AuthenticationService
     */
    private final AuthenticationService authenticationService;

    /**
     * Reference variable of MemberRepository
     */
    private final MemberRepository memberRepository;

    /**
     * Reference variable of TokenRepository
     */
    private final TokenRepository tokenRepository;

    @Override
    public String sendRegistrationEmail(EmailDetails email) {
        Optional<Member> member = memberRepository.findByUsername(email.getRecipient());
        if(!member.isPresent()) {
            String registrationToken = generateToken(email.getRecipient());
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            String message = "Poštovani/a," +
                    "\n " +
                    "\n U prilogu Vam šaljem link ka daljem procesu registracije: " + "http://localhost:3000/register" +
                    "\n Takođe u prilogu se nalazi i token koji ćete koristiti prilikom daljeg postupka registracije!" +
                    "\n Token: " + registrationToken +
                    "\n" +
                    "\n Srdačan pozdrav," +
                    "\n Vuk Manojlović 48/19";

            simpleMailMessage.setFrom("vukman619@gmail.com");
            simpleMailMessage.setTo(email.getRecipient());
            simpleMailMessage.setText(message);
            simpleMailMessage.setSubject("Registracija");

            mailSender.send(simpleMailMessage);
            return "Email je uspesno poslat";
        }
        else{
            return "Email vec postoji";
        }
    }

    @Override
    public String sendEmailChangePassword(EmailDetails email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        Optional<Member> member = memberRepository.findByUsername(email.getRecipient());
        String token = null;
        if(member.isPresent()) {
            List<Token> tokens = tokenRepository.findByMemberId(member.get().getId());
            token = tokens.get(0).getToken();
        }
        else{
            return "Email ne postoji";
        }
        String message = "Poštovani/a," +
                "\n " +
                "\n U prilogu Vam šaljem link ka daljem procesu promene lozinke: " + "http://localhost:3000/changePassword" +
                "\n Koristite token za neophodnu autentifikaciju prilikom promene lozinke" +
                "\n Token: " + token +
                "\n" +
                "\n Srdačan pozdrav," +
                "\n Vuk Manojlović 48/19";

        simpleMailMessage.setFrom("vukman619@gmail.com");
        simpleMailMessage.setTo(email.getRecipient());
        simpleMailMessage.setText(message);
        simpleMailMessage.setSubject("Promena lozinke");

        mailSender.send(simpleMailMessage);
        return "Email je uspesno poslat";
    }

    @Override
    public String sendEmailForgottenPassword(EmailDetails email) {
        if(memberRepository.findByUsername(email.getRecipient()).isPresent()){
            String token = generateToken(email.getRecipient());
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            String message = "Poštovani/a," +
                    "\n " +
                    "\n U prilogu Vam šaljem link ka daljem procesu promene Vaše zaboravljene lozinke: " + "http://localhost:3000/forgottenPassword" +
                    "\n Koristite token za neophodnu autentifikaciju prilikom promene lozinke" +
                    "\n Token: " + token +
                    "\n" +
                    "\n Srdačan pozdrav," +
                    "\n Vuk Manojlović 48/19";
            simpleMailMessage.setFrom("vukman619@gmail.com");
            simpleMailMessage.setTo(email.getRecipient());
            simpleMailMessage.setText(message);
            simpleMailMessage.setSubject("Promena zaboravljene lozinke");

            mailSender.send(simpleMailMessage);
            return "Email vec postoji";
        }
        else{
            return "Email ne postoji";
        }
    }

    @Override
    public String sendSimpleEmail(EmailDetails email) {
        return null;
    }

    @Override
    public String sendEmailWithAttachment(EmailDetails email) {
        return null;
    }

    /**
     * Generates token for authentication purposes of member.
     *
     * @param recipient email of member that needs token
     * @return String generated token
     */
    private String generateToken(String recipient) {
        Member member = new Member();
        member.setUsername(recipient);

        String token = jwtService.generateToken(member);
        authenticationService.saveMemberToken(null,token);

        return token;
    }
}
