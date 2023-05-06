package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * Represent the test that students take.
 * Contains content and author of the test.
 * One test can have multiple questions and belongs to only one author(Professor).
 * One test can be in multiple exams.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="test")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test implements Serializable {

    /**
     * Primary key of test entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    /**
     * Content of test.
     * Can't be null, blank or less than 2 characters.
     */
    @Column(name = "content")
    private String content;

    /**
     * References to the professor who created the test.
     */
    @JoinColumn(name="author",referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Professor author;

    /**
     * References to the multiple questions.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private Collection<QuestionTest> questionTestCollection;

    /**
     * References to the multiple exams.
     */
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "test")
    @JsonIgnore
    private Collection<Exam> examCollection;


}
