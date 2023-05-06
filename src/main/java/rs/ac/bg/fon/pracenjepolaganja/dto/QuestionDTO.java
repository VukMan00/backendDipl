package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionDTO{

    private Integer id;

    @NotBlank(message = "Content is mandatory")
    @Size(min = 2, message = "Content must have at least 2 characters")
    private String content;
}
