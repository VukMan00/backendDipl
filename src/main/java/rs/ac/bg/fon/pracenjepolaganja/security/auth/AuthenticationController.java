package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represent controller that process all authentication requests from user.
 * Intercept requests for registration and authentication.
 *
 * @author Vuk Manojlovic
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    /**
     * Reference variable of AuthenticationService.
     */
    private final AuthenticationService authenticationService;

    /**
     * Provides registration for members.
     *
     * @param request represent the request for registration from member
     * @return object of ResponseEntity when registration is completed
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * Provides authentication for members.
     *
     * @param request represent the request for authentication from member
     * @return object of ResponseEntity when authentication is completed
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
