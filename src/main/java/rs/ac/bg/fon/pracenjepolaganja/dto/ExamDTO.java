package rs.ac.bg.fon.pracenjepolaganja.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Represent Data Transfer Object of Exam entity.
 * Contains name of exam, date and amphitheater.
 *
 * @author Vuk Manojlovic
 */
@Data
public class ExamDTO{

    /**
     * Primary key of exam entity.
     */
    private Integer id;

    /**
     * Name of the exam.
     * Name can't be null, blank or be less than 2 characters.
     */
    @NotBlank(message = "Polje naziva polaganja je obavezno")
    @Size(min = 2, message = "Naziv polaganja mora da ima najmanje dva karaktera")
    private String name;

    /**
     * Date of exam.
     * Date must be in present date or in the future.
     * Date can't be null
     */
    @NotNull(message = "Datum polaganja je obavezno uneti")
    @FutureOrPresent(message = "Datum polaganja mora biti postavljeno na trenutni datum ili u buducnosti")
    private LocalDate date;

    /**
     * Amphitheater where exam is placed.
     * Can't be null, blank or less than 2 characters.
     */
    @NotBlank(message = "Polje naziva amfiteatra je obavezno uneti")
    private String amphitheater;

    /**
     * References to the Data Transfer Object of test.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private TestDTO test;

    /**
     * Represent students that are taking this exam
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<ResultExamDTO> results;

}
