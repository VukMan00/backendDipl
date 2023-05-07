package rs.ac.bg.fon.pracenjepolaganja;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Represents the class from which the project is launched.
 * Contains main method and bean for modelMapper.
 *
 * @author Vuk Manojlovic
 */
@SpringBootApplication
public class PracenjepolaganjaApplication {

	/**
	 * Bean for model mapper.
	 * Maps DTO to entities and vice versa.
	 *
	 * @return created bean for modelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(PracenjepolaganjaApplication.class, args);
	}

}
