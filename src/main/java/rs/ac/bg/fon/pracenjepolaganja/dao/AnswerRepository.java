package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

import java.util.List;

/**
 * Represent JPA repository for Answer entity.
 * Contains predefined methods of findAll, findById,
 * save and deleteById that are used in service implementation.
 *
 * @author Vuk Manojlovic
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, AnswerPK> {

    /**
     * Search for all answers that belongs to the question with given id.
     *
     * @param questionId id of question whose answers are needed.
     * @return list of answers.
     */
    List<Answer> findByQuestionId(Integer questionId);
}
