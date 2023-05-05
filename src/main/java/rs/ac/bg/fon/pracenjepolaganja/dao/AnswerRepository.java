package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, AnswerPK> {

    List<Answer> findByQuestionId(Integer questionId);
}
