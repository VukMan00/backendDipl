package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Represent exam where students take the test.
 * Contains name of exam, date and amphitheater where students take the test.
 * Exam can have multiple students and student can be on multiple exams.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam implements Serializable {

    /**
     * Primary key of exam entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    /**
     * Name of the exam.
     * Name can't be null, blank or be less than 2 characters.
     */
    @Column(name="name")
    private String name;

    /**
     * Date of exam.
     * Date must be in present date or in the future.
     * Date can't be null
     */
    @Column(name="date")
    private LocalDate date;

    /**
     * Amphitheater where exam is placed.
     * Can't be null, blank or less than 2 characters.
     */
    @Column(name="amphitheater")
    private String amphitheater;

    /**
     * References to the test that students take.
     */
    @JoinColumn(name="testId",referencedColumnName = "id")
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Test test;

    /**
     * References to the multiple students and their results of exam.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<ResultExam> resultExamCollection;
}
