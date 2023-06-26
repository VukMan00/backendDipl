package rs.ac.bg.fon.pracenjepolaganja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityTest {

    Authority authority;

    @BeforeEach
    void setUp() {
        authority = new Authority();
    }

    @AfterEach
    void tearDown(){
        authority = null;
    }

    @Test
    void setId() {
        authority.setId(1);
        assertEquals(1,authority.getId());
    }

    @Test
    void setName() {
        authority.setName("ROLE_USER");
        assertEquals("ROLE_USER",authority.getName());
    }

    @Test
    void setMember() {
        Member member = new Member();
        member.setUsername("vm20190048@student.fon.bg.ac.rs");
        member.setPassword("vukman00");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        member.setAuthorities(authorities);

        authority.setMember(member);
        assertEquals(member,authority.getMember());
        assertEquals("vm20190048@student.fon.bg.ac.rs",authority.getMember().getUsername());
        assertEquals("vukman00",authority.getMember().getPassword());
        assertEquals(authorities,authority.getMember().getAuthorities());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,ROLE_USER,ROLE_USER,1,1,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman00,true",
            "1,1,ROLE_USER,ROLE_USER,1,1,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman,false",
            "1,1,ROLE_USER,ROLE_USER,1,1,vm20190048@student.fon.bg.ac.rs,mm20190081@student.fon.bg.ac.rs,vukman00,vukman00,false",
            "1,1,ROLE_USER,ROLE_USER,1,2,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman00,false",
            "1,1,ROLE_USER,ROLE_ADMIN,1,1,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman00,false",
            "1,2,ROLE_USER,ROLE_USER,1,1,vm20190048@student.fon.bg.ac.rs,vm20190048@student.fon.bg.ac.rs,vukman00,vukman00,false"
    })
    void testEquals(Integer id1,Integer id2,String name1,String name2,Integer memberId1,Integer memberId2,String username1,String username2,String password1,String password2,boolean same) {
        Member member1 = new Member();
        member1.setId(memberId1);
        member1.setUsername(username1);
        member1.setPassword(password1);

        authority.setId(id1);
        authority.setName(name1);
        authority.setMember(member1);

        Member member2 = new Member();
        member2.setId(memberId2);
        member2.setUsername(username2);
        member2.setPassword(password2);

        Authority authority2 = new Authority();
        authority2.setId(id2);
        authority2.setName(name2);
        authority2.setMember(member2);

        assertEquals(authority.equals(authority2),same);
    }

    @Test
    void canEqual() {
        assertInstanceOf(Authority.class,authority);
    }

    @Test
    void testToString() {
        Member member = new Member();
        member.setId(2);
        member.setUsername("vm20190048@student.fon.bg.ac.rs");
        member.setPassword("vukman00");

        authority.setId(1);
        authority.setName("ROLE_USER");
        authority.setMember(member);

        String print = authority.toString();

        assertTrue(print.contains("1"));
        assertTrue(print.contains("ROLE_USER"));
        assertTrue(print.contains("2"));
        assertTrue(print.contains("vm20190048@student.fon.bg.ac.rs"));
        assertTrue(print.contains("vukman00"));
    }
}