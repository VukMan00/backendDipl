package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Represent Data Transfer Object of Professor entity.
 * Contains firstname,lastname and faculty email.
 *
 * @author Vuk Manojlovic
 */
@Data
public class ProfessorDTO {

    /**
     * Primary key of professor entity.
     */
    private Integer id;

    /**
     * Firstname of professor.
     * Can't be blank or null.
     */
    @NotBlank(message = "Polje ime je obavezno")
    private String name;

    /**
     * Lastname of professor.
     * Can't be blank or null.
     */
    @NotBlank(message = "Polje prezime je obavezno")
    private String lastname;

    /**
     * Faculty email of professor.
     * EmailDetails must be in valid form.
     */
    @NotBlank(message = "Polje email je obavezno")
    @Email(message = "Email mora biti u validnom formatu")
    private String email;

}
