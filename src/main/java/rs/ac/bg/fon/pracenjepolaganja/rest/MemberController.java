package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.RegistrationDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.MemberServiceImpl;

/**
 * Represent the controller that process all client requests for Member entity.
 * Contains endpoints for methods home(home page), registerMember and getUserDetailsAfterLogin.
 *
 * @author Vuk Manojlovic
 */
@RestController
public class MemberController {

    /**
     * Reference variable of MemberServiceImpl class.
     */
    private MemberServiceImpl memberService;

    @Autowired
    public MemberController(MemberServiceImpl memberService){
        this.memberService = memberService;
    }

    /**
     * Retrieves a string of home page for path "/".
     *
     * @return String of message
     */
    @GetMapping("/")
    public String home(){
        return "Welcome home!";
    }

    /**
     * Provides registration for members.
     *
     * @param registrationDTO registration object that contains information of student.
     * @return String message of successful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@Valid @RequestBody RegistrationDTO registrationDTO){
        return memberService.registerMember(registrationDTO);
    }

    /**
     * Retrieves credentials(username) of user.
     *
     * @param authentication object of user that is trying to log in.
     * @return Member object of log in user.
     */
    @GetMapping("/user")
    public Member getUserDetailsAfterLogin(Authentication authentication){
        return memberService.getUserDetailsAfterLogin(authentication);
    }
}
