package rs.ac.bg.fon.pracenjepolaganja.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class ExamDTO{

    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, message = "Name of exam must have at least 2 characters")
    private String name;

    @NotNull(message = "Date is mandatory")
    @FutureOrPresent(message = "Date of exam must be at current date or in the future")
    private Date date;

    @NotBlank(message = "Amphitheater is mandatory")
    @Size(min = 2, message = "Amphitheater must have at least 2 characters")
    private String amphitheater;

    private TestDTO test;

}
