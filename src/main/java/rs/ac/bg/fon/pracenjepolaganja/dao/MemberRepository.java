package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Integer> {
    List<Member> findByUsername(String username);
}
