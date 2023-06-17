package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Represent the student that solve test in one of the exams.
 * Contains firstname,lastname, index, date of birth and faculty email.
 * Student can take multiple exams.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    /**
     * Primary key of student entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    /**
     * Firstname of student.
     * Can't be null or blank.
     */
    @Column(name = "name")
    private String name;

    /**
     * Lastname of student.
     * Can't be null or blank.
     */
    @Column(name = "lastname")
    private String lastname;

    /**
     * Index of student.
     * Must be in form of 0000-0000 where
     * first four digits is first year of study and last four digits
     * are number of index.
     */
    @Column(name = "numberIndex",unique = true)
    private String index;

    /**
     * Date of birth.
     * Date can't be null or be ahead of current date.
     */
    @Column(name = "birth")
    @Temporal(TemporalType.DATE)
    private LocalDate birth;

    /**
     * Faculty email of student.
     * Email must be in valid form.
     */
    @Column(name = "email",unique=true)
    private String email;

    /**
     * References to the multiple exams that student can take.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<ResultExam> resultExamCollectionCollection;

    /**
     * References to the credentials of student
     */
    @JoinColumn(name="memberId",referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Member memberStudent;

}
