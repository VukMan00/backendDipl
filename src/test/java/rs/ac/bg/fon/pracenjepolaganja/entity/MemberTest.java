package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
    }

    @AfterEach
    void tearDown() {
        member = null;
    }

    @Test
    void setId() {
        member.setId(1);
        assertEquals(1,member.getId());
    }

    @Test
    void setUsername() {
        member.setUsername("vm20190048@student.fon.bg.ac.rs");
        assertEquals("vm20190048@student.fon.bg.ac.rs",member.getUsername());
    }

    @Test
    void setPassword() {
        member.setPassword("vukman00");
        assertEquals("vukman00",member.getPassword());
    }

    @Test
    void setAuthorities() {
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        authority.setMember(member);

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        member.setAuthorities(authorities);

        assertEquals(authorities,member.getAuthorities());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman00,true",
            "1,1,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman,false",
            "1,1,vm20190048@student.fon.bg.ac.rs,mm20190001@student.fon.bg.ac.rs,vukman00,vukman00,false",
            "1,2,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman00,false"
    })
    void testEquals(Integer id1,Integer id2,String username1,String username2,String password1,String password2,boolean same) {
        member.setId(id1);
        member.setUsername(username1);
        member.setPassword(password1);

        Member member2 = new Member();
        member2.setUsername(username2);
        member2.setId(id2);
        member2.setPassword(password2);

        assertEquals(member.equals(member2),same);
    }

    @Test
    void testToString() {
        member.setId(1);
        member.setUsername("vm20190048@student.fon.bg.ac.rs");
        member.setPassword("vukman00");

        String print = member.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("vm20190048@student.fon.bg.ac.rs"));
        assertTrue(print.contains("vukman00"));
    }
}