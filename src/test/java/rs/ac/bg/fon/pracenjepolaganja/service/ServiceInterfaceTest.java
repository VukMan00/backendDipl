package rs.ac.bg.fon.pracenjepolaganja.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;
import rs.ac.bg.fon.pracenjepolaganja.entity.Answer;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.AnswerPK;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ServiceInterfaceTest {

    protected ServiceInterface serviceInterface;

    @Test
    void findAllAnswers() {
        List<AnswerDTO> answers = serviceInterface.findAll();
        assertFalse(answers.isEmpty());
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }
}