package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Represent Data Transfer Object of QuestionTest association class.
 * Contains points that each question have in test.
 *
 * @author Vuk Manojlovic
 */
@Data
public class QuestionTestDTO {

    /**
     * References to Data Transfer Object of Question entity.
     */
    private QuestionDTO question;

    /**
     * References to Data Transfer Object of Test entity.
     */
    private TestDTO test;

    /**
     * Points of question in test.
     * Points must be in interval of 0 to 100.
     */
    @Min(value = 0, message = "Points should not be less than 0")
    @Max(value=100, message = "Points should not be greater than 100")
    private int points;
}
