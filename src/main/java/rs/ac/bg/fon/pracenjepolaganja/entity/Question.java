package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "question")
@Data
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="content")
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Collection<Answer> answers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private Collection<QuestionTest> questionTestsCollection;
}

