package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Represent Data Transfer Object of Student entity.
 * Contains firstname,lastname, index, date of birth and faculty email.
 *
 * @author Vuk Manojlovic
 */
@Data
public class StudentDTO{

    /**
     * Primary key of student entity.
     */
    private Integer id;

    /**
     * Firstname of student.
     * Can't be null or blank.
     */
    @NotBlank(message =  "Polje ime je obavezno")
    private String name;

    /**
     * Lastname of student.
     * Can't be null or blank.
     */
    @NotBlank(message = "Polje prezime je obavezno")
    private String lastname;

    /**
     * Index of student.
     * Must be in form of 0000-0000 where
     * first four digits is first year of study and last four digits
     * are number of index.
     */
    @Pattern(regexp = "[1-9][0-9]{3}-[0-9]{4}",message = "Broj indeksa nije u validnom formatu")
    private String index;

    /**
     * Date of birth.
     * Date can't be null or be ahead of current date.
     */
    @PastOrPresent(message = "Datum rodjenja ne moze biti kasnije od trenutnog datuma")
    private LocalDate birth;

    /**
     * Faculty email of student.
     * EmailDetails must be in valid form.
     */
    @NotBlank(message = "Polje email je obavezno")
    @Email(message = "Email mora biti u validnom formatu")
    private String email;

    /**
     * Credentials of student
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MemberDTO member;

    /**
     * Represent exams which student is taking
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<ResultExamDTO> results;
}
