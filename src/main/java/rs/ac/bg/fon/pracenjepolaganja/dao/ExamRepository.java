package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;

import java.util.List;

/**
 * Represent JPA repository of Exam entity.
 * Contains predefined methods of findAll, findById,
 * save and deleteById that are used in service implementation.
 *
 * @author Vuk Manojlovic
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam,Integer> {

    /**
     * Search for all exams that belongs to the test with given id.
     *
     * @param testId id of test whose exams are needed.
     * @return list of exams.
     */
    List<Exam> findByTestId(Integer testId);

}
