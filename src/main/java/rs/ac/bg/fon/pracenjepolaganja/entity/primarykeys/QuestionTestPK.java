package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class QuestionTestPK {

    @Basic(optional = false)
    private Integer questionId;

    @Basic(optional = false)
    private Integer testId;
}
