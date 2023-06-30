package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.io.IOException;

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
     * @param registrationRequest represent the request for registration from member
     * @return object of ResponseEntity when registration is completed
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest){
        return ResponseEntity.ok(authenticationService.registration(registrationRequest));
    }


    /**
     * Provides authentication for members.
     *
     * @param request represent the request for authentication from member
     * @return object of ResponseEntity when authentication is completed
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(@Valid @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    /**
     * Changes the password of member.
     * Password must be in strong format.
     * @throws NotFoundException when member with given username doesn't exist
     */
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@Valid @RequestBody RequestChangePassword request) throws NotFoundException {
        return authenticationService.changePassword(request);
    }

    /**
     * Refreshes the token, generates new access token.
     *
     * @param request object for refreshing new token
     * @param response response object after refreshing
     * @throws IOException throws when refreshing is not valid
     */
    @PostMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
