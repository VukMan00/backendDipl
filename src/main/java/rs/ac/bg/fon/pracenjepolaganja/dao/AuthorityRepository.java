package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Authority;

import java.util.Set;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
    Set<Authority> findByMemberId(Integer id);
}
