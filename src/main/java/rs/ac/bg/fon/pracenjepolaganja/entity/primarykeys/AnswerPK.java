package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represent complex answer primary key that contains id from answer and question.
 *
 * @author Vuk Manojlovic
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerPK implements Serializable {

    /**
     * Primary key of entity answer.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    private Integer answerId;

    /**
     * Primary key of entity answer and foreign key that reference to question.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Basic(optional = false)
    private Integer questionId;
}
