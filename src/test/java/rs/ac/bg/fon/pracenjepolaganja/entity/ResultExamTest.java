package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ResultExamTest {

    ResultExam resultExam;

    @BeforeEach
    void setUp() {
        resultExam = new ResultExam();
    }

    @AfterEach
    void tearDown() {
        resultExam = null;
    }

    @Test
    void setResultExamPK() {
        ResultExamPK resultExamPK = new ResultExamPK(1,2);
        resultExam.setResultExamPK(resultExamPK);
        assertEquals(resultExamPK,resultExam.getResultExamPK());
    }

    @Test
    void setPoints() {
        resultExam.setPoints(10);
        assertEquals(10,resultExam.getPoints());
    }

    @Test
    void setGrade() {
        resultExam.setGrade(7);
        assertEquals(7,resultExam.getGrade());
    }

    @Test
    void setExam() {
        Exam exam = new Exam();
        exam.setId(1);
        exam.setName("Exam");
        exam.setAmphitheater("B104");
        exam.setDate(LocalDate.of(2020,9,3));
        resultExam.setExam(exam);

        assertEquals(exam,resultExam.getExam());
    }

    @Test
    void setStudent() {
        Student student = new Student();
        student.setId(1);
        student.setName("John");
        student.setLastname("Green");
        student.setEmail("john@gmail.com");
        student.setIndex("2019-0048");
        student.setBirth(LocalDate.of(2000,6,21));
        resultExam.setStudent(student);

        assertEquals(student,resultExam.getStudent());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2,2,60,60,7,7,true",
            "1,1,2,2,60,60,7,8,false",
            "1,1,2,2,60,65,7,7,false",
            "1,1,2,3,60,60,7,7,false",
            "1,2,2,2,60,60,7,7,false",
            "1,2,2,3,60,65,7,8,false",
    })
    void testEquals(int examId1,int examId2,int studentId1,int studentId2,int points1,int points2,int grade1,int grade2,boolean same) {
        resultExam.setResultExamPK(new ResultExamPK(examId1,studentId1));
        resultExam.setPoints(points1);
        resultExam.setGrade(grade1);

        ResultExam resultExam2 = new ResultExam();
        resultExam2.setResultExamPK(new ResultExamPK(examId2,studentId2));
        resultExam2.setPoints(points2);
        resultExam2.setGrade(grade2);

        assertEquals(resultExam.equals(resultExam2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(ResultExam.class,resultExam);
    }

    @Test
    void testToString() {
        resultExam.setResultExamPK(new ResultExamPK(1,2));
        resultExam.setPoints(10);
        resultExam.setGrade(8);

        String print = resultExam.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("2"));
        assertTrue(print.contains("10"));
        assertTrue(print.contains("8"));
    }
}