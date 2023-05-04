package rs.ac.bg.fon.pracenjepolaganja.dto;

import java.util.Date;

public record StudentDTO(Integer id, String name, String lastname, String index, Date birth, String email) {
}
