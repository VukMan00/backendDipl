package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TestTest {

    rs.ac.bg.fon.pracenjepolaganja.entity.Test test;
    @BeforeEach
    void setUp() {
        test = new rs.ac.bg.fon.pracenjepolaganja.entity.Test();
    }

    @AfterEach
    void tearDown() {
        test = null;
    }

    @Test
    void setId() {
        test.setId(1);
        assertEquals(1,test.getId());
    }

    @Test
    void setContent() {
        test.setContent("Software development");
        assertEquals("Software development",test.getContent());
    }

    @Test
    void setAuthor() {
        Professor professor = new Professor();
        professor.setId(1);
        professor.setName("John");
        professor.setLastname("Green");
        professor.setEmail("john@gmail.com");
        test.setAuthor(professor);

        assertEquals(professor,test.getAuthor());
    }

    @Test
    void setQuestionTestCollection() {
        QuestionTest questionTest = new QuestionTest();
        questionTest.setQuestionTestPK(new QuestionTestPK(2,1));
        questionTest.setPoints(10);

        Collection<QuestionTest> questionsTests = new ArrayList<>();
        questionsTests.add(questionTest);

        test.setQuestionTestCollection(questionsTests);
        assertEquals(questionsTests,test.getQuestionTestCollection());
    }

    @Test
    void setExamCollection() {
        Exam exam = new Exam();
        exam.setName("Exam");
        exam.setId(1);
        exam.setAmphitheater("b104");
        exam.setDate(LocalDate.of(2020,2,24));

        Collection<Exam> exams = new ArrayList<>();
        exams.add(exam);

        test.setExamCollection(exams);
        assertEquals(exams,test.getExamCollection());
    }

    @ParameterizedTest
    @CsvSource({
          "1,1,Test1,Test1,true",
          "1,1,Test1,Test2,false",
          "1,2,Test1,Test1,false",
          "1,2,Test1,Test2,false"
    })
    void testEquals(int id1,int id2,String content1,String content2,boolean same) {
        test.setId(id1);
        test.setContent(content1);

        rs.ac.bg.fon.pracenjepolaganja.entity.Test test2 = new rs.ac.bg.fon.pracenjepolaganja.entity.Test();
        test2.setId(id2);
        test2.setContent(content2);
        assertEquals(test.equals(test2),same);
    }


    @Test
    void testToString() {
        test.setId(1);
        test.setContent("Test1");

        String print = test.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("Test1"));
    }
}