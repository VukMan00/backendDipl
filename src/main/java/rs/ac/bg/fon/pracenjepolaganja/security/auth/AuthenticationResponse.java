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
     * Represent JWT token of member.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    /**
     * Represent message for member
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseEntity<String> message;

}
