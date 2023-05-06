package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class StudentDTO{

    private Integer id;

    @NotBlank(message =  "Firstname is mandatory")
    private String name;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @Pattern(regexp = "[1-9][0-9]{3}-[0-9]{4}")
    private String index;

    @PastOrPresent(message = "Date can't be ahead of the current date")
    private Date birth;

    @Email(message = "Email must be valid")
    private String email;
}
