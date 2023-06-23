package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {

    Exam exam;

    @BeforeEach
    void setUp(){
        exam = new Exam();
    }

    @AfterEach
    void tearDown(){
        exam = null;
    }

    @Test
    void setId() {
        exam.setId(1);
        assertEquals(1,exam.getId());
    }

    @Test
    void setName() {
        exam.setName("Exam from mathematics");
        assertEquals("Exam from mathematics",exam.getName());
    }

    @Test
    void setDate() {
        LocalDate date = LocalDate.of(2020,5,20);
        exam.setDate(date);
        assertEquals(date,exam.getDate());
    }

    @Test
    void setAmphitheater() {
        exam.setAmphitheater("B104");
        assertEquals("B104",exam.getAmphitheater());
    }

    @Test
    void setTest() {
        rs.ac.bg.fon.pracenjepolaganja.entity.Test test = new rs.ac.bg.fon.pracenjepolaganja.entity.Test();
        test.setContent("Informatics test");
        exam.setTest(test);

        assertEquals(test,exam.getTest());
        assertEquals("Informatics test",exam.getTest().getContent());

    }

    @Test
    void setResultExamCollection() {
        ResultExam result = new ResultExam();
        result.setExam(exam);
        result.setGrade(10);
        result.setPoints(80);

        Collection<ResultExam> results = new ArrayList<>();
        results.add(result);

        exam.setResultExamCollection(results);
        assertEquals(results,exam.getResultExamCollection());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,Exam,Exam,2000-01-21,2000-01-21,B104,B104,true",
            "1,1,Exam,Exam,2000-01-21,2000-01-21,B104,C002,false",
            "1,1,Exam,Exam,2000-01-21,2000-01-20,B104,B104,false",
            "1,1,Exam,Exam1,2000-01-21,2000-01-21,B104,B104,false",
            "1,2,Exam,Exam,2000-01-21,2000-01-21,B104,B104,false",
    })
    void testEquals(int examId1, int examId2, String name1, String name2, LocalDate date1, LocalDate date2, String amphitheater1, String amphitheater2, boolean same) {
        exam.setId(examId1);
        exam.setName(name1);
        exam.setDate(date1);
        exam.setAmphitheater(amphitheater1);

        Exam exam2 = new Exam();
        exam2.setId(examId2);
        exam2.setName(name2);
        exam2.setDate(date2);
        exam2.setAmphitheater(amphitheater2);

        assertEquals(exam.equals(exam2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(Exam.class,exam);
    }

    @Test
    void testToString() {
        exam.setId(1);
        exam.setName("Exam");
        exam.setDate(LocalDate.of(2020,6,20));
        exam.setAmphitheater("B104");

        String print = exam.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("Exam"));
        assertTrue(print.contains(LocalDate.of(2020,6,20).toString()));
        assertTrue(print.contains("B104"));
    }
}