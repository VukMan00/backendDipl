package rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ResultExamPKTest {

    ResultExamPK resultExamPK;

    @BeforeEach
    void setUp() {
        resultExamPK = new ResultExamPK();
    }

    @AfterEach
    void tearDown() {
        resultExamPK = null;
    }

    @Test
    void setExamId() {
        resultExamPK.setExamId(1);
        assertEquals(1,resultExamPK.getExamId());
    }

    @Test
    void setStudentId() {
        resultExamPK.setStudentId(1);
        assertEquals(1,resultExamPK.getStudentId());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2,2,true",
            "1,1,2,3,false",
            "1,2,2,2,false",
            "1,2,2,3,false"
    })
    void testEquals(int examId1,int examId2,int studentId1,int studentId2,boolean same) {
        resultExamPK.setExamId(examId1);
        resultExamPK.setStudentId(studentId1);

        ResultExamPK resultExamPK2 = new ResultExamPK(examId2,studentId2);
        assertEquals(resultExamPK.equals(resultExamPK2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(ResultExamPK.class,resultExamPK);
    }

    @Test
    void testToString() {
        resultExamPK.setExamId(1);
        resultExamPK.setStudentId(2);

        String print = resultExamPK.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("2"));
    }
}