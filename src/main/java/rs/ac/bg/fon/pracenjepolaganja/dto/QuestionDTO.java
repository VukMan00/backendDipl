package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Represent Data Transfer Object of Question entity.
 * Contains content of question.
 *
 * @author Vuk Manojlovic
 */
@Data
public class QuestionDTO{

    /**
     * Primary key of question entity.
     */
    private Integer id;

    /**
     * Content of question.
     * Can't be null,blank or less than 2 characters.
     */
    @NotBlank(message = "Content is mandatory")
    @Size(min = 2, message = "Content must have at least 2 characters")
    private String content;
}
