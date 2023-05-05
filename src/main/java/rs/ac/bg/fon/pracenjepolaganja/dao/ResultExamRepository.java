package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;

@Repository
public interface ResultExamRepository extends JpaRepository<ResultExam, ResultExamPK> {
}
