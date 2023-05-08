package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;

import java.io.Serializable;

/**
 * Represent association class of exam and student.
 * Contains points and grade that student achieved in exam.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="resultexam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultExam implements Serializable {

    /**
     * Complex primary key that contains id from exam and student.
     */
    @EmbeddedId
    private ResultExamPK resultExamPK;

    /**
     * Points that student achieved in exam.
     * Points must be in interval of 0 to 100.
     */
    @Column(name="points")
    private Integer points;

    /**
     * Grade that student achieved in exam based on points.
     * Grade must be in interval of 5 to 10.
     */
    @Column(name="grade")
    private Integer grade;

    /**
     * References to the exam that student take.
     */
    @JoinColumn(name = "examId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Exam exam;

    /**
     * References to student in exam.
     */
    @JoinColumn(name = "studentId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Student student;

}
