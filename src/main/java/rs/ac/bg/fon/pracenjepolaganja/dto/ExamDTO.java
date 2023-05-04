package rs.ac.bg.fon.pracenjepolaganja.dto;

import java.util.Date;

public record ExamDTO(Integer id, String name, Date date,String amphitheater, TestDTO testDTO) {
}
