package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class AnswerPK implements Serializable {

    @Basic(optional = false)
    private Integer id;

    @Basic(optional = false)
    private Integer questionId;

    public AnswerPK(){

    }

    public AnswerPK(int id,int questionId){
        this.id = id;
        this.questionId = questionId;
    }
}
