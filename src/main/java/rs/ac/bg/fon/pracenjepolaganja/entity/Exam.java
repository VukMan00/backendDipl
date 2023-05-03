package rs.ac.bg.fon.pracenjepolaganja.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="exam")
@Data
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
    private Collection<ResultExam> resultExamCollection;
}
