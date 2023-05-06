package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represent complex primary key of association class QuestionTest.
 *
 * @author Vuk Manojlovic
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTestPK implements Serializable {

    /**
     * Primary key of entity QuestionTest and
     * foreign key that reference to question.
     */
    @Basic(optional = false)
    private Integer questionId;

    /**
     * Primary key of entity QuestionTest and
     * foreign key that reference to test.
     */
    @Basic(optional = false)
    private Integer testId;
}
