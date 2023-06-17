package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represent Data Transfer Object for registration purposes.
 * Contains name, lastname, email, password, index and date of birth.
 * Registration is used only for students with role of User.
 * Administrators already have accounts.
 *
 * @author Vuk Manojlovic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    /**
     * Firstname of student.
     * Can't be null or blank.
     */
    @NotBlank(message =  "Firstname is mandatory")
    private String name;

    /**
     * Lastname of student.
     * Can't be null or blank.
     */
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    /**
     * Faculty email of student.
     * Email must be in valid form.
     * Email is also username for student.
     */
    @Email(message = "Email must be valid")
    private String email;

    /**
     * Password of student account.
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
