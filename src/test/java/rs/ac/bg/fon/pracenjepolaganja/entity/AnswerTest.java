package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    Answer answer;

    @BeforeEach
    void setUp() throws Exception {
        answer = new Answer();
    }

    @AfterEach
    void tearDown() throws Exception {
        answer = null;
    }

    @Test
    void setAnswerPK() {
        AnswerPK answerPK = new AnswerPK(1,2);
        answer.setAnswerPK(answerPK);
        assertEquals(1,answer.getAnswerPK().getAnswerId());
        assertEquals(2,answer.getAnswerPK().getQuestionId());
    }

    @Test
    void setContent() {
        answer.setContent("Yes");
        assertEquals("Yes",answer.getContent());
    }

    @Test
    void setSolution() {
        answer.setSolution(true);
        assertEquals(true,answer.isSolution());
    }

    @Test
    void setQuestion() {
        Question question = new Question();
        question.setContent("Is Java platform independent programing language?");
        answer.setQuestion(question);
        assertEquals("Is Java platform independent programing language?",answer.getQuestion().getContent());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2,2,Yes,Yes,true,true,true",
            "1,1,2,2,Yes,Yes,true,false,false",
            "1,1,2,2,Yes,No,true,true,false",
            "1,1,2,3,Yes,Yes,true,true,false",
            "1,3,2,2,Yes,Yes,true,true,false",
    })
    void testEquals(int answerId1,int answerId2,int questionId1,int questionId2,
                    String content1, String content2, boolean solution1, boolean solution2, boolean same) {
        AnswerPK answerPK1 = new AnswerPK(answerId1,questionId1);
        AnswerPK answerPK2 = new AnswerPK(answerId2,questionId2);

        answer.setAnswerPK(answerPK1);
        answer.setContent(content1);
        answer.setSolution(solution1);

        Answer answer2 = new Answer();
        answer2.setAnswerPK(answerPK2);
        answer2.setContent(content2);
        answer2.setSolution(solution2);

        assertEquals(answer.equals(answer2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(Answer.class,answer);
    }

    @Test
    void testToString() {
        answer.setContent("Yes");
        answer.setSolution(true);
        answer.setAnswerPK(new AnswerPK(1,1));

        String print = answer.toString();

        assertTrue(print.contains("Yes"));
        assertTrue(print.contains("true"));
        assertTrue(print.contains("1"));
        assertTrue(print.contains("1"));
    }
}