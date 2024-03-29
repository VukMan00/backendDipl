package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;

/**
 * Represent Data Transfer Object of ResultExam association class.
 * Contains points and grade that student got in exam.
 *
 * @author Vuk Manojlovic
 */
@Data
public class ResultExamDTO {

    /**
     * Reference to the primary key of ResultExam entity
     */
    private ResultExamPK resultExamPK;
    /**
     * References to the Data Transfer Object of Student entity.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StudentDTO student;

    /**
     * References to the Data Transfer Object of Exam entity.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExamDTO exam;

    /**
     * Points that student achieved in exam.
     * Points must be in interval of 0 to 100.
     */
    @Min(value = 0, message = "Neispravno unet broj poena")
    private Integer points;

    /**
     * Grade that student achieved in exam based on points.
     * Grade must be in interval of 5 to 10.
     */
    @Min(value = 5, message = "Ocena mora biti u intervalu od 5 do 10")
    @Max(value=10, message = "Ocena mora biti u intervalu od 5 do 10")
    private Integer grade;
}
