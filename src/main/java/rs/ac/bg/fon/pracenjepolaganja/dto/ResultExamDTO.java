package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;

@Data
public class ResultExamDTO {

    private StudentDTO student;
    private ExamDTO exam;
    private Integer points;
    private Integer grade;
}
