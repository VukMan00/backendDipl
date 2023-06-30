package rs.ac.bg.fon.pracenjepolaganja.security.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.security.auth.AuthenticationService;
import rs.ac.bg.fon.pracenjepolaganja.security.config.JwtService;
import rs.ac.bg.fon.pracenjepolaganja.security.token.Token;
import rs.ac.bg.fon.pracenjepolaganja.security.token.TokenType;

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

    @Override
    public String sendRegistrationEmail(EmailDetails email) {
        String registrationToken = generateRegistrationToken(email.getRecipient());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String message = "Poštovani," +
                "\n " +
                "\n U prilogu Vam šaljem link ka daljem procesu registracije: " + "localhost:3000/register" +
                "\n Takođe u prilogu se nalazi i token koji ćete koristiti prilikom daljeg postupka registracije!" +
                "\n Token: " + registrationToken +
                "\n" +
                "\nSrdačan pozdrav," +
                "\n Vuk Manojlović 48/19";

        simpleMailMessage.setFrom("vukman619@gmail.com");
        simpleMailMessage.setTo(email.getRecipient());
        simpleMailMessage.setText(message);
        simpleMailMessage.setSubject("Registracija");

        mailSender.send(simpleMailMessage);
        return "Email is sent successfully";
    }

    @Override
    public String sendSimpleEmail(EmailDetails email) {
        return null;
    }

    @Override
    public String sendMailWithAttachment(EmailDetails email) {
        return null;
    }

    /**
     * Generates registration token for member.
     *
     * @param recipient email of member that needs registration token
     * @return String generated registration token
     */
    private String generateRegistrationToken(String recipient) {
        Member member = new Member();
        member.setUsername(recipient);

        String registerToken = jwtService.generateToken(member);
        authenticationService.saveMemberToken(null,registerToken);

        return registerToken;
    }
}
