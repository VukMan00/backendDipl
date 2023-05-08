package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.QuestionTestPK;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTestTest {

    QuestionTest questionTest;

    @BeforeEach
    void setUp() {
        questionTest = new QuestionTest();
    }

    @AfterEach
    void tearDown() {
        questionTest = null;
    }

    @Test
    void setQuestionTestPK() {
        QuestionTestPK questionTestPK = new QuestionTestPK(1,1);
        questionTest.setQuestionTestPK(questionTestPK);
        assertEquals(questionTestPK,questionTest.getQuestionTestPK());
    }

    @Test
    void setPoints() {
        questionTest.setPoints(10);
        assertEquals(10,questionTest.getPoints());
    }

    @Test
    void setQuestion() {
        Question question = new Question();
        question.setContent("Is Java platform independent?");
        question.setId(1);
        questionTest.setQuestion(question);

        assertEquals(question,questionTest.getQuestion());
    }

    @Test
    void setTest() {
        rs.ac.bg.fon.pracenjepolaganja.entity.Test test = new rs.ac.bg.fon.pracenjepolaganja.entity.Test();
        test.setId(1);
        test.setContent("Informatics test");
        questionTest.setTest(test);

        assertEquals(test,questionTest.getTest());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,1,1,10,10,true",
            "1,1,1,1,10,20,false",
            "1,1,1,2,10,10,false",
            "1,2,1,1,10,10,false",
            "1,2,1,3,10,20,false",

    })
    void testEquals(int questionId1,int questionId2,int testId1,int testId2,int points1,int points2,boolean same) {
        questionTest.setQuestionTestPK(new QuestionTestPK(questionId1,testId1));
        questionTest.setPoints(points1);

        QuestionTest questionTest2 = new QuestionTest();
        questionTest2.setQuestionTestPK(new QuestionTestPK(questionId2,testId2));
        questionTest2.setPoints(points2);

        assertEquals(questionTest.equals(questionTest2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(QuestionTest.class,questionTest);
    }

    @Test
    void testToString() {
        questionTest.setQuestionTestPK(new QuestionTestPK(1,2));
        questionTest.setPoints(10);

        String print = questionTest.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("2"));
        assertTrue(print.contains("10"));
    }
}