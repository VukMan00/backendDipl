package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;

import java.io.Serializable;

@Entity
@Table(name="questiontest")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTest implements Serializable {

    @EmbeddedId
    protected QuestionTestPK questionTestPK;

    @Column(name="points")
    private Integer points;

    @JoinColumn(name = "questionId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Question question;

    @JoinColumn(name = "testId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Test test;

}
