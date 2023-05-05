package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

import java.io.Serializable;

@Entity
@Table(name="answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer implements Serializable {

    @EmbeddedId
    protected AnswerPK answerPK;

    @Column(name="content")
    private String content;

    @Column(name="solution")
    private boolean solution;

    @JoinColumn(name="questionId",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private Question question;

    public Answer(AnswerPK answerPK){
        this.answerPK = answerPK;
    }

    public Answer(int id,int questionId){
        this.answerPK = new AnswerPK(id,questionId);
    }

    public Answer(AnswerPK answerPK, String content, boolean solution) {
        this.answerPK = answerPK;
        this.content = content;
        this.solution = solution;
    }
}
