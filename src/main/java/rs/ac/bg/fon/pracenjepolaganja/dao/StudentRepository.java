package rs.ac.bg.fon.pracenjepolaganja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.pracenjepolaganja.entity.Student;

/**
 * Represent JPA repository for Student entity.
 * Contains predefined methods of findAll, findById,
 * save and deleteById that are used in service implementation.
 *
 * @author Vuk Manojlovic
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    /**
     * Retrieves student with given email.
     *
     * @param email of student that is needed
     * @return student with given email
     */
    Student findByEmail(String email);

    /**
     * Retrieves student with given index.
     *
     * @param index of student that is needed
     * @return student with given index
     */
    Student findByIndex(String index);
}
