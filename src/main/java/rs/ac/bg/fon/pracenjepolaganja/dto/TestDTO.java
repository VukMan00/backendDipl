package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
    @NotBlank(message = "Content is mandatory")
    @Size(min = 2, message = "Content must have at least 2 characters")
    private String content;

    /**
     * References to Data Transfer Object of Professor entity.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfessorDTO professor;
}
