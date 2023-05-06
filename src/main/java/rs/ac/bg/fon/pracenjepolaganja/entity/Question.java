package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * Represent question of test.
 * Contains content of question.
 * One question can be in multiple tests and also have multiple answers.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {

    /**
     * Primary key of question entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Content of question.
     * Can't be null,blank or less than 2 characters.
     */
    @Column(name="content")
    private String content;

    /**
     * References to multiple answers that question can have.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Collection<Answer> answers;

    /**
     * References to the tests where question belongs and also to the points
     * that each question have in test.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    @JsonIgnore
    private Collection<QuestionTest> questionTestsCollection;
}

