package rs.ac.bg.fon.pracenjepolaganja.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentDTO{

    private Integer id;
    private String name;
    private String lastname;
    private String index;
    private Date birth;
    private String email;
}
