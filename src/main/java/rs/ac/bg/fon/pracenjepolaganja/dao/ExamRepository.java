package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam,Integer> {
}
