package tech.bergen;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.format.DateTimeFormatter;

// https://stackoverflow.com/questions/60747639/how-to-create-apache-artemis-queues-in-code-and-use-them-with-jms

@SpringBootApplication
public class Main {

	@Getter
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
