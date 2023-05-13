package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AnswerPKTest {
    AnswerPK answerPK;

    @BeforeEach
    void setUp() {
        answerPK = new AnswerPK();
    }

    @AfterEach
    void tearDown() {
        answerPK = null;
    }

    @Test
    void setAnswerId() {
        answerPK.setAnswerId(1);
        assertEquals(1,answerPK.getAnswerId());
    }

    @Test
    void setQuestionId() {
        answerPK.setQuestionId(1);
        assertEquals(1,answerPK.getQuestionId());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2,2,true",
            "1,1,2,3,false",
            "1,2,2,2,false",
            "1,2,2,3,false"
    })
    void testEquals(int answerId1,int answerId2,int questionId1,int questionId2,boolean same) {
        answerPK.setAnswerId(answerId1);
        answerPK.setQuestionId(questionId1);

        AnswerPK answerPK2 = new AnswerPK(answerId2,questionId2);
        assertEquals(answerPK.equals(answerPK2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(AnswerPK.class,answerPK);
    }

    @Test
    void testToString() {
        answerPK.setAnswerId(1);
        answerPK.setQuestionId(2);

        String print = answerPK.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("2"));
    }
}