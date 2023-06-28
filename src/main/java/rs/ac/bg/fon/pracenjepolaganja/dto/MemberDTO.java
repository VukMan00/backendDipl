package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;
import rs.ac.bg.fon.pracenjepolaganja.entity.Role;

/**
 * Represent Data Transfer Object of Member entity.
 * Contains username,password and set of authorities that member has.
 *
 * @author Vuk Manojlovic
 */
@Data
public class MemberDTO {

    /**
     * Primary key of member entity
     */
    private Integer id;

    /**
     * Username of member.
     * Username is unique.
     */
    private String username;

    /**
     * Password of member.
     * Every password is encrypted.
     */
    private String password;

    /**
     * Role that member has.
     * Role that member can have is USER or ADMIN.
     */
    private Role role;
}
