package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;

import java.io.Serializable;

/**
 * Represent association class of question and test.
 * Contains points that each question have in test.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="questiontest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionTest implements Serializable {

    /**
     * Complex primary key that contains id from question and test.
     */
    @EmbeddedId
    protected QuestionTestPK questionTestPK;

    /**
     * Points of question in test.
     * Points must be in interval of 0 to 100.
     */
    @Column(name="points")
    private Integer points;

    /**
     * References to one question of test.
     */
    @JoinColumn(name = "questionId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Question question;

    /**
     * References to one test that have question.
     */
    @JoinColumn(name = "testId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Test test;

}
