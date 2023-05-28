package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.bg.fon.pracenjepolaganja.dao.AnswerRepository;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterfaceTest;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerServiceImplTest extends ServiceInterfaceTest {

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        serviceInterface = new AnswerServiceImpl(answerRepository);
    }

    @AfterEach
    void tearDown() {
        serviceInterface = null;
    }
}