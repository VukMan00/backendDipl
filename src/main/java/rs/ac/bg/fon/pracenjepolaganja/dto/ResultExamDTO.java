package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ResultExamDTO {

    private StudentDTO student;
    private ExamDTO exam;

    @Min(value = 0, message = "Points should not be less than 0")
    @Max(value=100, message = "Points should not be greater than 100")
    private Integer points;

    @Min(value = 5, message = "Grade should not be less than 5")
    @Max(value=10, message = "Grade should not be greater than 10")
    private Integer grade;
}
