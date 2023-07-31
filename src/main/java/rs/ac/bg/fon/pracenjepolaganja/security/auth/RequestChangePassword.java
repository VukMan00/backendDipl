package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Requests change for password of member.
 * Contains new password.
 *
 * @author Vuk Manojlovic
 */
@Data
public class RequestChangePassword {

    /**
     * Username of member whose password is going to change
     */
    private String username;

    /**
     * Represent old password of member account
     */
    private String oldPassword;

    /**
     * Password of member account.
     * Password is mandatory.
     * A digit must occur at least once
     * A lower case letter must occur at least once
     * An upper case letter must occur at least once
     * A special character must occur at least once
     * No whitespace allowed in the entire string
     * At least 8 characters
     *
     */
    @NotBlank(message = "Lozinka je obavezna")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Slaba lozinka")
    private String newPassword;
}
