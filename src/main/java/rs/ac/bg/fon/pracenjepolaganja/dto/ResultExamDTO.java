package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Represent Data Transfer Object of ResultExam association class.
 * Contains points and grade that student got in exam.
 *
 * @author Vuk Manojlovic
 */
@Data
public class ResultExamDTO {

    /**
     * References to the Data Transfer Object of Student entity.
     */
    private StudentDTO student;

    /**
     * References to the Data Transfer Object of Exam entity.
     */
    private ExamDTO exam;

    /**
     * Points that student achieved in exam.
     * Points must be in interval of 0 to 100.
     */
    @Min(value = 0, message = "Points should not be less than 0")
    @Max(value=100, message = "Points should not be greater than 100")
    private Integer points;

    /**
     * Grade that student achieved in exam based on points.
     * Grade must be in interval of 5 to 10.
     */
    @Min(value = 5, message = "Grade should not be less than 5")
    @Max(value=10, message = "Grade should not be greater than 10")
    private Integer grade;
}
