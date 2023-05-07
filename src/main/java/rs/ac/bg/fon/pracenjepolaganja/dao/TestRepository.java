package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Test;

import java.util.List;

/**
 * Represent JPA repository for Test entity.
 * Contains predefined methods of findAll, findById,
 * save and deleteById that are used in service implementation.
 *
 * @author Vuk Manojlovic
 */
@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {

    /**
     * Search for all tests that belongs to the professor with given id.
     *
     * @param author id of professor whose tests are needed.
     * @return list of tests.
     */
    List<Test> findByAuthor(Integer author);
}
