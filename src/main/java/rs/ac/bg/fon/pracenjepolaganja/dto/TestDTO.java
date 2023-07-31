package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collection;

/**
 * Represent Data Transfer Object of Test entity.
 * Contains content of test.
 *
 * @author Vuk Manojlovic
 */
@Data
public class TestDTO{

    /**
     * Primary key of test entity
     */
    private Integer id;

    /**
     * Content of test.
     * Can't be null, blank or less than 2 characters.
     */
    @NotBlank(message = "Polje naziva pitanja je obavezno")
    @Size(min = 2, message = "Naziv pitanja mora imati minimum 2 karaktera")
    private String content;

    /**
     * References to Data Transfer Object of Professor entity.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private ProfessorDTO author;

    /**
     * Questions that test has
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<QuestionTestDTO> questions;
}
