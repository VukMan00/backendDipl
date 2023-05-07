package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.QuestionTest;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;

import java.util.List;

/**
 * Represent JPA repository of QuestionTest association class.
 * Contains predefined methods of findAll, findById,
 * save and deleteById that are used in service implementation.
 *
 * @author Vuk Manojlovic
 */
@Repository
public interface QuestionTestRepository extends JpaRepository<QuestionTest, QuestionTestPK> {
    /**
     * Search for all QuestionTest entities that have the given test id.
     *
     * @param testId id of test whose questions are needed.
     * @return list of QuestionTest entities.
     */
    List<QuestionTest> findByTestId(Integer testId);

    /**
     * Search for all QuestionTest entities that have the given question id.
     *
     * @param questionId id of question whose tests are needed.
     * @return list of QuestionTest entities.
     */
    List<QuestionTest> findByQuestionId(Integer questionId);
}
