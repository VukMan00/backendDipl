package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTestPKTest {

    QuestionTestPK questionTestPK;

    @BeforeEach
    void setUp() {
        questionTestPK = new QuestionTestPK();
    }

    @AfterEach
    void tearDown() {
        questionTestPK = null;
    }

    @Test
    void setQuestionId() {
        questionTestPK.setQuestionId(1);
        assertEquals(1,questionTestPK.getQuestionId());
    }

    @Test
    void setTestId() {
        questionTestPK.setTestId(1);
        assertEquals(1,questionTestPK.getTestId());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2,2,true",
            "1,1,2,3,false",
            "1,2,2,2,false",
            "1,2,2,3,false"
    })
    void testEquals(int questionId1,int questionId2,int testId1,int testId2,boolean same) {
        questionTestPK.setQuestionId(questionId1);
        questionTestPK.setTestId(testId1);

        QuestionTestPK questionTestPK2 = new QuestionTestPK(questionId2,testId2);
        assertEquals(questionTestPK.equals(questionTestPK2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(QuestionTestPK.class,questionTestPK);
    }

    @Test
    void testToString() {
        questionTestPK.setQuestionId(1);
        questionTestPK.setTestId(2);

        String print = questionTestPK.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("2"));
    }
}