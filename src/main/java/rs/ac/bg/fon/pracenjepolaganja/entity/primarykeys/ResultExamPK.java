package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ResultExamPK {

    @Basic(optional = false)
    private Integer examId;

    @Basic(optional = false)
    private Integer studentId;

}
