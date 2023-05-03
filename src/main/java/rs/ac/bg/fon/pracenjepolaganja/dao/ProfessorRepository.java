package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor,Integer> {
}
