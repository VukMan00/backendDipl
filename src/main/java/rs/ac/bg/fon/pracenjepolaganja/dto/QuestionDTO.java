package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;

/**
 * Represent Data Transfer Object of Question entity.
 * Contains content of question.
 *
 * @author Vuk Manojlovic
 */
@Data
public class QuestionDTO{

    /**
     * Primary key of question entity.
     */
    private Integer id;

    /**
     * Content of question.
     * Can't be null,blank or less than 2 characters.
     */
    @NotBlank(message = "Polje naziva pitanja je obavezno")
    @Size(min = 2, message = "Naziv pitanja mora da ima najmanje dva karaktera")
    private String content;

    /**
     * Represent tests where question belongs.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<QuestionTestDTO> tests;

    /**
     * Represent answers of question
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<AnswerDTO> answers;

    @JsonIgnore
    public Collection<QuestionTestDTO> getTests(){
        return tests;
    }

    @JsonProperty
    public void setTests(Collection<QuestionTestDTO> tests){
        this.tests = tests;
    }
}
