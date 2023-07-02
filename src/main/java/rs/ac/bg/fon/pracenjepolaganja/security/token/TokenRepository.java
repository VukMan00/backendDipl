package rs.ac.bg.fon.pracenjepolaganja.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository of Token entity.
 * Provides CRUD operations for tokens of members.
 *
 * @author Vuk Manojlovic
 */
@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    /**
     * Retrieves all valid token.
     * Valid token is one that is not expired or revoked.
     *
     * @param id of member whose token is needed
     * @return list of tokens for given member
     */
    @Query(value = """
      select t from Token t inner join Member m\s
      on t.member.id = m.id\s
      where m.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByMember(Integer id);

    /**
     * Retrieves tokens with given member id.
     *
     * @param id of member whose tokens are needed
     * @return list of token with given member id
     */
    List<Token> findByMemberId(Integer id);

    /**
     * Retrieves one token who matches given string of token.
     *
     * @param token String of token whose needed
     * @return token found token
     */
    Optional<Token> findByToken(String token);

    /**
     * Deletes token with given member id.
     *
     * @param id of member whose token is going to be deleted
     */
    void deleteByMemberId(Integer id);
}
