package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfessorDTO {

    private Integer id;

    @NotBlank(message = "Firstname is mandatory")
    private String name;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @Email(message = "Email must be valid")
    private String email;

}
