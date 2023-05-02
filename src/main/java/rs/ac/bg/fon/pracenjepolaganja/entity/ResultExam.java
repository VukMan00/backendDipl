package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;

import java.io.Serializable;

@Entity
@Table(name="resultexam")
@Data
public class ResultExam implements Serializable {

    @EmbeddedId
    private ResultExamPK resultExamPK;

    @Column(name="points")
    private Integer points;

    @Column(name="grade")
    private Integer grade;

    @JoinColumn(name = "examId", referencedColumnName = "examId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Exam exam;

    @JoinColumn(name = "studentId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;

}