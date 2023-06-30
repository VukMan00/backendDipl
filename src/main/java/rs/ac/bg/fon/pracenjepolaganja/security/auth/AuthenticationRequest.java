package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent Request for authentication from member.
 * Contains username nad password of member.
 *
 * @author Vuk Manojlovic
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * Represent username of member.
     * Username is in form of valid student faculty email.
     */
    @Email(message = "EmailDetails must be valid")
    private String username;

    /**
     * Represent password of member.
     * Password must have at least 2 characters.
     */
    @NotBlank(message = "Password is mandatory")
    private String password;
}
