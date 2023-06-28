package rs.ac.bg.fon.pracenjepolaganja.security.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represent object of registration that member sent.
 * Contains firstname,lastname,email,password and index.
 *
 * @author Vuk Manojlovic
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

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
     * Email must be in valid form.
     * Email is also username for member.
     */
    @Email(message = "Email must be valid")
    private String email;

    /**
     * Password of member account.
     * Password is mandatory and must be at least 2 characters length.
     */
    @NotBlank(message = "Password is mandatory")
    @Size(min = 2, message = "Content must have at least 2 characters")
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
}
