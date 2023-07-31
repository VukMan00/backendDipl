package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

/**
 * Represent Data Transfer Object of Answer entity.
 * Contains content and solution.
 *
 * @author Vuk Manojlovic
 */
@Data
public class AnswerDTO {

    /**
     * Complex primary key that contains id from answer and question.
     */
    private AnswerPK answerPK;

    /**
     * Content of question.
     * Content can't be blank, null or have less than 2 characters
     */
    @NotBlank(message = "Polje naziva odgovora je obavezno")
    @Size(min = 2, message = "Naziv odgovora mora da ima najmanje dva karaktera")
    private String content;

    /**
     * Solution of question.
     * Represent if answer is correct or incorrect.
     */
    private boolean solution;

    /**
     * References to the Data Transfer Object of question that answer belongs to.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private QuestionDTO question;

}
