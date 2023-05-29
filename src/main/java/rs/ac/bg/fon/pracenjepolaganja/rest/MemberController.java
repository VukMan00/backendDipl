package rs.ac.bg.fon.pracenjepolaganja.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return memberService.registerMember(registrationDTO);
    }
}
