package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.RegistrationDTO;
import rs.ac.bg.fon.pracenjepolaganja.service.impl.MemberServiceImpl;

@RestController
public class MemberController {

    private MemberServiceImpl memberService;

    @Autowired
    public MemberController(MemberServiceImpl memberService){
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@Valid @RequestBody RegistrationDTO registrationDTO){
        memberService.registerMember(registrationDTO);
        return new ResponseEntity<>("Member is successfully registered", HttpStatus.CREATED);
    }
}
