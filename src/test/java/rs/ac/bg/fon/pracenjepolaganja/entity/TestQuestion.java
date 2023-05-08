package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TestQuestion {

    Question question;

    @BeforeEach
    void setUp() {
        question = new Question();
    }

    @AfterEach
    void tearDown() {
        question = null;
    }

    @Test
    void setId() {
        question.setId(1);
        assertEquals(1,question.getId());
    }

    @Test
    void setContent() {
        question.setContent("Is Java platform independent?");
        assertEquals("Is Java platform independent?",question.getContent());
    }

    @Test
    void setAnswers() {
        Answer answer = new Answer();
        answer.setContent("Yes");
        answer.setSolution(true);

        Answer answer1 = new Answer();
        answer1.setContent("No");
        answer1.setSolution(false);

        Collection<Answer> answers = new ArrayList<>();
        answers.add(answer);
        answers.add(answer1);

        question.setAnswers(answers);

        assertEquals(answers,question.getAnswers());
    }

    @Test
    void setQuestionTestsCollection() {
        QuestionTest questionTest = new QuestionTest();
        question.setContent("Is Java platform independent");

        rs.ac.bg.fon.pracenjepolaganja.entity.Test test = new rs.ac.bg.fon.pracenjepolaganja.entity.Test();
        test.setContent("Java test");
        questionTest.setQuestion(question);
        questionTest.setTest(test);

        Collection<QuestionTest> questionsTests = new ArrayList<>();
        questionsTests.add(questionTest);
        question.setQuestionTestsCollection(questionsTests);

        assertEquals(questionsTests,question.getQuestionTestsCollection());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,Is Java platform independent?,Is Java platform independent?,true",
            "1,1,Is Java platform independent?,Is C# platform independent?,false",
            "1,2,Is Java platform independent?,Is JAVA platform independent?,false",
            "1,2,Is Java platform independent?,Is C# platform independent?,false",
    })
    void testEquals(int id1,int id2,String content1,String content2,boolean same) {
        question.setId(id1);
        question.setContent(content1);

        Question question2 = new Question();
        question2.setId(id2);
        question2.setContent(content2);

        assertEquals(question.equals(question2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(Question.class,question);
    }

    @Test
    void testToString() {
        question.setId(1);
        question.setContent("Is Java platform independent?");

        String print = question.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("Is Java platform independent?"));
    }
}