package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

import java.io.Serializable;

/**
 * Represent answer of question in test.
 * Contains content, solution(true or false) of question.
 * One Answer belongs only to one question where question can have multiple answers.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer implements Serializable {

    /**
     * Complex primary key that contains id from answer and question.
     */
    @EmbeddedId
    protected AnswerPK answerPK;

    /**
     * Content of question.
     * Content can't be blank, null or have less than 2 characters
     */
    @Column(name="content")
    private String content;

    /**
     * Solution of question.
     * Represent if answer is correct or incorrect.
     */
    @Column(name="solution")
    private boolean solution;

    /**
     * References to the question that answer belongs to.
     */
    @JoinColumn(name="questionId",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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
