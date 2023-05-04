package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ExamDTO{

    private Integer id;
    private String name;
    private Date date;
    private String amphitheater;
    private TestDTO test;

}
