package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {
    List<Test> findByAuthor(Integer author);
}
