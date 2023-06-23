package rs.ac.bg.fon.pracenjepolaganja.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

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
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        Authority authority = (Authority) o;
        return Objects.equals(getId(), authority.getId()) && Objects.equals(getName(), authority.getName()) && Objects.equals(getMember(), authority.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getMember());
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", member=" + member +
                '}';
    }
}
