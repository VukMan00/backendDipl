package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerPK implements Serializable {

    @Basic(optional = false)
    private Integer answerId;

    @Basic(optional = false)
    private Integer questionId;
}
