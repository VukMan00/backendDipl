package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    @NotBlank(message =  "Firstname is mandatory")
    private String name;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 2, message = "Content must have at least 2 characters")
    private String password;

    @Pattern(regexp = "[1-9][0-9]{3}-[0-9]{4}")
    private String index;

    @PastOrPresent(message = "Date can't be ahead of the current date")
    private LocalDate birth;

}
