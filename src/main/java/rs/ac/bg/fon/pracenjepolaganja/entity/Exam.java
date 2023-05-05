package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="date")
    private Date date;

    @Column(name="amphitheater")
    private String amphitheater;

    @JoinColumn(name="testId",referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Test test;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    @JsonIgnore
    private Collection<ResultExam> resultExamCollection;
}
