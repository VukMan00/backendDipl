package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;

import java.util.List;

/**
 * Represent JPA repository of Member entity.
 * MemberRepository is used to store information(credentials) of
 * users in application.
 *
 * @author Vuk Manojlovic
 */
public interface MemberRepository extends JpaRepository<Member,Integer> {

    /**
     * Search for Member in database with given username.
     * Every member has unique username.
     *
     * @param username of Member whose needed.
     * @return List of Members with given username
     */
    List<Member> findByUsername(String username);
}
