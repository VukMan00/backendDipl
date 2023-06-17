package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Represent authorities of users.
 * Authority that user has can be in form of User and Admin.
 * Contains name(role) and reference to member(user).
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="authorities")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority implements Serializable {

    /**
     * Primary key of Authority entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    /**
     * Role of user.
     * Role can be User or Admin.
     */
    @Column(name="name")
    private String name;

    /**
     * Reference to the member.
     */
    @ManyToOne
    @JoinColumn(name = "memberId",referencedColumnName = "id")
    @JsonIgnore
    private Member member;
}
