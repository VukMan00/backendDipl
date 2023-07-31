package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;

/**
 * Represent Data Transfer Object of QuestionTest association class.
 * Contains points that each question have in test.
 *
 * @author Vuk Manojlovic
 */
@Data
public class QuestionTestDTO {

    /**
     * Reference to primary key of QuestionTest entity
     */
    private QuestionTestPK questionTestPK;

    /**
     * References to Data Transfer Object of Question entity.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QuestionDTO question;

    /**
     * References to Data Transfer Object of Test entity.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TestDTO test;

    /**
     * Points of question in test.
     * Points must be in interval of 0 to 100.
     */
    @Min(value = 0, message = "Broj poena ne moze biti manji od 0")
    @Max(value=100, message = "Broj poena ne moze biti veci od 100")
    private int points;
}
