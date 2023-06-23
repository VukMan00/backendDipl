package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;

/**
 * Represent Data Transfer Object of Authority entity.
 * Contains name of role that member has.
 *
 * @author Vuk Manojlovic
 */
@Data
public class AuthorityDTO {

    /**
     * Primary key of authority entity.
     */
    private Integer id;

    /**
     * Name of the role that member has.
     * Can be USER or ADMIN role.
     */
    private String name;

}
