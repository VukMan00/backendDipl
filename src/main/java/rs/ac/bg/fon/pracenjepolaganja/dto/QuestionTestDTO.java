package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;

@Data
public class QuestionTestDTO {
    private QuestionDTO question;
    private TestDTO test;
    private int points;
}
