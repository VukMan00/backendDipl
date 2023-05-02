package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Embeddable
@Data
public class AnswerPK {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Basic(optional = false)
    private Integer id;

    @Basic(optional = false)
    private Integer questionId;


    public AnswerPK(int id,int questionId){
        this.id = id;
        this.questionId = questionId;
    }
}
