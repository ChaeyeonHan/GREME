package itstime.shootit.greme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GremeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GremeApplication.class, args);
	}

}
