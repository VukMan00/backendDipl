package rs.ac.bg.fon.pracenjepolaganja.security.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;

import java.io.Serializable;

/**
 * Represent token entity of member.
 * Contains string of token, token type, boolean value of expired and revoked, reference to member
 *
 * @author Vuk Manojlovic
 */
@Entity
@Table(name="token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {

    /**
     * Primary key of token entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    /**
     * Name of the token.
     */
    @Column(name="token",unique = true)
    public String token;

    /**
     * Token type.
     * Can be Bearer.
     */
    @Enumerated(EnumType.STRING)
    @Column(name="tokenType")
    public TokenType tokenType = TokenType.BEARER;

    /**
     * Is token revoked.
     */
    public boolean revoked;

    /**
     * Is token expired.
     */
    public boolean expired;

    /**
     * Reference to member.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId",referencedColumnName = "id")
    public Member member;
}
