package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * Represent the professor who is responsible for creating a test.
 * Contains firstname, lastname and faculty email.
 * One professor can create multiple tests.
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="professor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor implements Serializable {

    /**
     * Primary key of professor entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Firstname of professor.
     * Can't be blank or null.
     */
    @Column(name = "name")
    private String name;

    /**
     * Lastname of professor.
     * Can't be blank or null.
     */
    @Column(name = "lastname")
    private String lastname;

    /**
     * Faculty email of professor.
     * Email must be in valid form.
     */
    @Column(name = "email")
    private String email;

    /**
     * References to the multiple tests that professor created.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<Test> tests;

    /**
     * References to the credentials of professor
     */
    @JoinColumn(name="memberId",referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Member memberProfessor;

}
