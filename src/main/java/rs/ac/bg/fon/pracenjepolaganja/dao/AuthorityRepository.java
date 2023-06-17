package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Authority;

import java.util.Set;

/**
 * Represent JPA repository of Authority entity.
 * AuthorityRepository contains roles that every user has in application.
 *
 * @author Vuk Manojlovic
 */
public interface AuthorityRepository extends JpaRepository<Authority,Integer> {

    /**
     * Search for Authorities that Member with certain id has.
     *
     * @param id of Member whose authority is needed
     * @return Set of Authorities for the Member with given id.
     */
    Set<Authority> findByMemberId(Integer id);
}
