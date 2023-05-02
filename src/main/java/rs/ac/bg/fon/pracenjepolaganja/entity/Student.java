package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.enums.Gender;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "student")
@Data
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "index")
    private String index;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth")
    @Temporal(TemporalType.DATE)
    private Date birth;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<ResultExam> resultExamCollectionCollection;

}
