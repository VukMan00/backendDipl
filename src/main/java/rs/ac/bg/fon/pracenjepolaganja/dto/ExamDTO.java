package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

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
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, message = "Name of exam must have at least 2 characters")
    private String name;

    /**
     * Date of exam.
     * Date must be in present date or in the future.
     * Date can't be null
     */
    @NotNull(message = "Date is mandatory")
    @FutureOrPresent(message = "Date of exam must be at current date or in the future")
    private LocalDate date;

    /**
     * Amphitheater where exam is placed.
     * Can't be null, blank or less than 2 characters.
     */
    @NotBlank(message = "Amphitheater is mandatory")
    @Size(min = 2, message = "Amphitheater must have at least 2 characters")
    private String amphitheater;

    /**
     * References to the Data Transfer Object of test.
     */
    private TestDTO test;

}
