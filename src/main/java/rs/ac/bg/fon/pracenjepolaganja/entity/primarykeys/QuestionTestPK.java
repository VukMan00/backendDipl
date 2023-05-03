package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class QuestionTestPK implements Serializable {

    @Basic(optional = false)
    private Integer questionId;

    @Basic(optional = false)
    private Integer testId;
}
