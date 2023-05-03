package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ResultExamPK implements Serializable {

    @Basic(optional = false)
    private Integer examId;

    @Basic(optional = false)
    private Integer studentId;

}
