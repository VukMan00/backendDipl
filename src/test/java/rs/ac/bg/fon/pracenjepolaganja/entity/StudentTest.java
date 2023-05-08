package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
    }

    @AfterEach
    void tearDown() {
        student = null;
    }

    @Test
    void setId() {
        student.setId(1);
        assertEquals(1,student.getId());
    }

    @Test
    void setName() {
        student.setName("John");
        assertEquals("John",student.getName());
    }

    @Test
    void setLastname() {
        student.setLastname("Green");
        assertEquals("Green",student.getLastname());
    }

    @Test
    void setIndex() {
        student.setIndex("2019-0048");
        assertEquals("2019-0048",student.getIndex());
    }

    @Test
    void setBirth() {
        LocalDate date = LocalDate.of(2001,2,26);
        student.setBirth(date);
        assertEquals(date,student.getBirth());
    }

    @Test
    void setEmail() {
        student.setEmail("john@gmail.com");
        assertEquals("john@gmail.com",student.getEmail());
    }

    @Test
    void setResultExamCollectionCollection() {
        ResultExam resultExam = new ResultExam();
        resultExam.setResultExamPK(new ResultExamPK(1,1));
        resultExam.setGrade(10);
        resultExam.setPoints(100);

        Collection<ResultExam> results = new ArrayList<>();
        results.add(resultExam);
        student.setResultExamCollectionCollection(results);

        assertEquals(results,student.getResultExamCollectionCollection());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,John,John,Green,Green,2019-0048,2019-0048,2000-02-20,2000-02-20,john@gmail.com,john@gmail.com,true",
            "1,1,John,John,Green,Green,2019-0048,2019-0048,2000-02-20,2000-02-20,john@gmail.com,green@gmail.com,false",
            "1,1,John,John,Green,Green,2019-0048,2019-0048,2000-02-20,2000-02-21,john@gmail.com,john@gmail.com,false",
            "1,1,John,John,Green,Green,2019-0048,2019-0025,2000-02-20,2000-02-20,john@gmail.com,john@gmail.com,false",
            "1,1,John,John,Green,LaVine,2019-0048,2019-0048,2000-02-20,2000-02-20,john@gmail.com,john@gmail.com,false",
            "1,1,John,Zach,Green,Green,2019-0048,2019-0048,2000-02-20,2000-02-20,john@gmail.com,john@gmail.com,false",
            "1,2,John,John,Green,Green,2019-0048,2019-0048,2000-02-20,2000-02-20,john@gmail.com,john@gmail.com,false",
            "1,2,John,Zach,Green,LaVine,2019-0048,2019-0025,2000-02-20,2000-02-21,john@gmail.com,green@gmail.com,false",
    })
    void testEquals(int studentId1,int studentId2,String name1,String name2,String lastname1,String lastname2,String index1,String index2,
                    LocalDate date1,LocalDate date2,String email1,String email2,boolean same) {
        student.setId(studentId1);
        student.setName(name1);
        student.setLastname(lastname1);
        student.setIndex(index1);
        student.setBirth(date1);
        student.setEmail(email1);

        Student student2 = new Student();
        student2.setId(studentId2);
        student2.setName(name2);
        student2.setLastname(lastname2);
        student2.setIndex(index2);
        student2.setBirth(date2);
        student2.setEmail(email2);

        assertEquals(student.equals(student2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(Student.class,student);
    }

    @Test
    void testToString() {
        student.setId(1);
        student.setName("John");
        student.setLastname("Green");
        student.setIndex("2019-0023");
        LocalDate date = LocalDate.of(2020,10,2);
        student.setBirth(date);
        student.setEmail("john@gmail.com");

        String print = student.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("John"));
        assertTrue(print.contains("Green"));
        assertTrue(print.contains("2019-0023"));
        assertTrue(print.contains("2020-10-02"));
        assertTrue(print.contains("john@gmail.com"));
    }
}