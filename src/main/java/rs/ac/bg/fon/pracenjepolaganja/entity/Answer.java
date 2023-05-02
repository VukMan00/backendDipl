package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

import java.io.Serializable;

@Entity
@Table(name="answer")
@Data
public class Answer implements Serializable {

    @EmbeddedId
    protected AnswerPK answerPK;

    @Column(name="content")
    private String content;

    @Column(name="solution")
    private boolean solution;

    @JoinColumn(name="questionId",referencedColumnName = "questionId")
    @ManyToOne(optional = false)
    private Question question;

    public Answer(){}

    public Answer(AnswerPK answerPK){
        this.answerPK = answerPK;
    }

    public Answer(int id,int questionId){
        this.answerPK = new AnswerPK(id,questionId);
    }
}
