package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {

    Professor professor;

    @BeforeEach
    void setUp() {
        professor = new Professor();
    }

    @AfterEach
    void tearDown() {
        professor = null;
    }

    @Test
    void setName() {
        professor.setName("John");
        assertEquals("John",professor.getName());
    }

    @Test
    void setLastname() {
        professor.setLastname("Green");
        assertEquals("Green",professor.getLastname());
    }

    @Test
    void setEmail() {
        professor.setEmail("josh@gmail.com");
        assertEquals("josh@gmail.com",professor.getEmail());
    }

    @Test
    void setTests() {
        rs.ac.bg.fon.pracenjepolaganja.entity.Test test = new rs.ac.bg.fon.pracenjepolaganja.entity.Test();
        Collection<rs.ac.bg.fon.pracenjepolaganja.entity.Test> tests =
                new ArrayList<>();
        test.setContent("Informatics");

        tests.add(test);
        professor.setTests(tests);
        assertEquals(tests,professor.getTests());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,John,John,Green,Green,josh@gmail.com,josh@gmail.com,true",
            "1,1,John,John,Green,Green,josh@gmail.com,green@gmail.com,false",
            "1,1,John,John,Green,Curry,josh@gmail.com,josh@gmail.com,false",
            "1,1,John,Zach,Green,Green,josh@gmail.com,josh@gmail.com,false",
            "1,2,John,John,Green,Green,josh@gmail.com,josh@gmail.com,false"
    })
    void testEquals(int id1,int id2,String name1,String name2,String lastname1,String lastname2,String email1,String email2,boolean same) {
        professor.setId(id1);
        professor.setName(name1);
        professor.setLastname(lastname1);
        professor.setEmail(email1);

        Professor professor2 = new Professor();
        professor2.setId(id2);
        professor2.setName(name2);
        professor2.setLastname(lastname2);
        professor2.setEmail(email2);

        assertEquals(professor.equals(professor2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(Professor.class,professor);
    }

    @Test
    void testToString() {
        professor.setId(1);
        professor.setName("John");
        professor.setLastname("Green");
        professor.setEmail("john@gmail.com");

        String print = professor.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("John"));
        assertTrue(print.contains("Green"));
        assertTrue(print.contains("john@gmail.com"));
    }
}