package rs.ac.bg.fon.pracenjepolaganja.entity;

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

    @JoinColumn(name="autor",referencedColumnName = "autor")
    @ManyToOne(optional = false)
    private Professor autor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private Collection<QuestionTest> questionTestCollection;

    @OneToMany(mappedBy = "id")
    private Collection<Exam> examCollection;


}
