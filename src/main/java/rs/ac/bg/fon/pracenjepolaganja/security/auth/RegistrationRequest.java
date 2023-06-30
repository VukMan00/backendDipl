package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class RegistrationRequest {

    /**
     * Firstname of member.
     * Can't be null or blank.
     */
    @NotBlank(message =  "Firstname is mandatory")
    private String firstname;

    /**
     * Lastname of member.
     * Can't be null or blank.
     */
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    /**
     * Faculty email of member.
     * EmailDetails must be in valid form.
     * EmailDetails is also username for member.
     */
    @Email(message = "EmailDetails must be valid")
    private String email;

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
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "This isn't strong password")
    private String password;

    /**
     * Index of student.
     * Must be in form of 0000-0000 where
     * first four digits is first year of study and last four digits
     * are number of index.
     */
    @Pattern(regexp = "[1-9][0-9]{3}-[0-9]{4}")
    private String index;

    /**
     * Date of birth.
     * Date can't be null or be ahead of current date.
     */
    @PastOrPresent(message = "Date can't be ahead of the current date")
    private LocalDate birth;

    /**
     * Represent registrationToken of member.
     */
    @NotBlank(message="Registration token is mandatory")
    private String registrationToken;
}
