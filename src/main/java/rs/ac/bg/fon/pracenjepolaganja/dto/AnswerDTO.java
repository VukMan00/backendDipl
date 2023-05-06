package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

@Data
public class AnswerDTO {

    private AnswerPK answerPK;

    @NotBlank(message = "Content is mandatory")
    @Size(min = 2, message = "Content must have at least 2 characters")
    private String content;

    private boolean solution;

    private QuestionDTO question;

}
