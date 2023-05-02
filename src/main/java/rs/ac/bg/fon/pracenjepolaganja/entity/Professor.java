package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.enums.Gender;
import rs.ac.bg.fon.pracenjepolaganja.entity.enums.Rank;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="professor")
@Data
public class Professor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name="rank")
    private Rank rank;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
    private Collection<Test> tests;

}
