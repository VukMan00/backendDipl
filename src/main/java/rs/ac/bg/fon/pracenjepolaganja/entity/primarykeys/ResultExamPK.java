package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represent complex primary key of association class ResultExam.
 *
 * @author Vuk Manojlovic
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultExamPK implements Serializable {

    /**
     * Primary key of entity ResultExam and
     * foreign key that reference to exam.
     */
    @Basic(optional = false)
    private Integer examId;

    /**
     * Primary key of entity ResultExam and
     * foreign key that reference to student.
     */
    @Basic(optional = false)
    private Integer studentId;
}
