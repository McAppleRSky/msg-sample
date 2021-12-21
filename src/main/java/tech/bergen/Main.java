package tech.bergen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.format.DateTimeFormatter;

// https://stackoverflow.com/questions/60747639/how-to-create-apache-artemis-queues-in-code-and-use-them-with-jms

@SpringBootApplication
public class Main {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter getDateTimeFormatter(){return dateTimeFormatter;};

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	/*public static Logger configureLog4j(Class<?> clazz){
		ConfigurationBuilder<BuiltConfiguration> cfgBuilder = ConfigurationBuilderFactory.newConfigurationBuilder();
		cfgBuilder
				.add(cfgBuilder
						.newAppender("Stdout", "CONSOLE")
						*//*.add(cfgBuilder
                                .newLayout("PatternLayout")
                                .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"))*//*
						.addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT))
				.add(cfgBuilder
						.newAppender("fileAppender", "FILE")
						.add(cfgBuilder
								.newLayout("PatternLayout")
								.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"))
						.addAttribute("fileName", "log/server.log"))
		;
		RootLoggerComponentBuilder rootLoggerBuilder = cfgBuilder
				.newRootLogger(Level.ALL)
				.add(cfgBuilder
						.newAppenderRef("Stdout")
						.addAttribute("level", Level.INFO))
				.add(cfgBuilder
						.newAppenderRef("fileAppender")
						.addAttribute("level", Level.INFO))
				;
		LoggerContext context = Configurator
				.initialize(cfgBuilder
						.add(rootLoggerBuilder)
						.build());
		return context.getLogger(clazz);}*/
}
