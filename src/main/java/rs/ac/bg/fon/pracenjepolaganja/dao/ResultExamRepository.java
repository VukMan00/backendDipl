package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;

import java.util.List;

/**
 * Represent JPA repository of ResultExam association class.
 * Contains predefined methods of findAll, findById,
 * save and deleteById that are used in service implementation.
 *
 * @author Vuk Manojlovic
 */
@Repository
public interface ResultExamRepository extends JpaRepository<ResultExam, ResultExamPK> {
    /**
     * Search for all ResultExam entities that have the given exam id.
     *
     * @param examId id of exam whose results are needed.
     * @return list of ResultExam entities.
     */
    List<ResultExam> findByExamId(Integer examId);

    /**
     * Search for all ResultExam entities that have the given student id.
     *
     * @param studentId id of student whose results are needed.
     * @return list of ResultExam entities.
     */
    List<ResultExam> findByStudentId(Integer studentId);
}
