package tech.bergen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.format.DateTimeFormatter;

// https://stackoverflow.com/questions/60747639/how-to-create-apache-artemis-queues-in-code-and-use-them-with-jms

@SpringBootApplication
//@EnableScheduling
public class Main {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter getDateTimeFormatter(){return dateTimeFormatter;};

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
