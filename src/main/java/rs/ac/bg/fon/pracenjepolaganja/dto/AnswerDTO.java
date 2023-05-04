package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;


public record AnswerDTO(AnswerPK answerPK, String content, boolean solution, QuestionDTO questionDTO) {

}
