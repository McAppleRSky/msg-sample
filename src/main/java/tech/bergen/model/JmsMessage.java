package tech.bergen.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class JmsMessage {

	protected int id;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime dateTime;
	protected String body;

	@Override
	public String toString() {
		return "{\"id\":\"" + id
				+ "\",\"dtime\":\"" + dateTime.format(tech.bergen.Main.getDateTimeFormatter())
				+ "\",\"body\":\"" + body
				+ "\"}";
	}

	public JmsMessage() {
	}

	public JmsMessage(int id, LocalDateTime dateTime, String body) {
		this.id = id;
		this.dateTime = dateTime;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getBody() {
		return body;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
