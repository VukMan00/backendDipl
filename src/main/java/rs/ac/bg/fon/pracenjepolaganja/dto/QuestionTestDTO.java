package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class QuestionTestDTO {
    private QuestionDTO question;
    private TestDTO test;

    @Min(value = 0, message = "Points should not be less than 0")
    @Max(value=100, message = "Points should not be greater than 100")
    private int points;
}
