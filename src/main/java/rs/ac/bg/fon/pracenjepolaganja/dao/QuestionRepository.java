package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
}
