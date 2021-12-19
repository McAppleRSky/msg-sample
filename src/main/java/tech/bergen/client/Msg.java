package tech.bergen.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Data
@Getter @Setter
public class Msg {
	private int id;
	private LocalDateTime dateTime;
	private String body;
	private String queueName;

	@Override
	public String toString() {
		return "Msg {" +
					"id=" + id +
					", dtime='" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + '\'' +
					", body='" + body + '\'' +
					", qname='" + queueName + '\'' +
				'}';
	}
	
	
}
