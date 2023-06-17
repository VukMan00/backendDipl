package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.dto.RegistrationDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.MemberServiceImpl;

@RestController
public class MemberController {

    private MemberServiceImpl memberService;

    @Autowired
    public MemberController(MemberServiceImpl memberService){
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(){
        return "Welcome home!";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@Valid @RequestBody RegistrationDTO registrationDTO){
        return memberService.registerMember(registrationDTO);
    }

    @GetMapping("/user")
    public Member getUserDetailsAfterLogin(Authentication authentication){
        return memberService.getUserDetailsAfterLogin(authentication);
    }
}
