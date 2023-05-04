package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

@Data
public class AnswerDTO {

    private AnswerPK answerPK;
    private String content;
    private boolean solution;
    private QuestionDTO question;

}
