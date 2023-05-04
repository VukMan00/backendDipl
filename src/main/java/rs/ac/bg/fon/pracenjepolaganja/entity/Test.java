package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="test")
@Data
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @JoinColumn(name="author",referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Professor author;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private Collection<QuestionTest> questionTestCollection;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "test")
    @JsonIgnore
    private Collection<Exam> examCollection;


}
