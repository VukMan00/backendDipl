package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

/**
 * Response after authentication is completed.
 * Contains JWT token, generated during authentication.
 *
 * @author Vuk Manojlovic
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * Represent JWT access token of member.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accessToken;

    /**
     * Represent JWT refresh token of member
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;

    /**
     * Represent message for member
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    /**
     * Represent firstname of member.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstname;

    /**
     * Represent lastname of member.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastname;

    /**
     * Represent email of member.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    /**
     * Represent index of member.
     * Index only exist withing students.
     * When authenticated member is professor, index will be null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String index;

    /**
     * Represent role of member.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;


}
