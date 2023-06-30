package rs.ac.bg.fon.pracenjepolaganja.security.email;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represent the controller that process all request for emails.
 * Intercept request for preRegister endpoint, that is used for to send member email for registration purposes.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmailController {

    /**
     * Reference variable of EmailService
     */
    private final EmailService emailService;

    /**
     * Sends email to member that is going to register.
     *
     * @param email object that contains email of member that is going to register.
     * @return ResponseEntity object of string if email is sent successfully
     */
    @PostMapping("/preRegister")
    public ResponseEntity<String> preRegister(@Valid @RequestBody EmailDetails email){
        return ResponseEntity.ok(emailService.sendRegistrationEmail(email));
    }
}
