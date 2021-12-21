package tech.bergen.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class JmsMessage {

	private int id;
	@JsonProperty("date_time")
	private String dateTime;
	private String body;

	@Override
	public String toString() {
		return "{\"id\":\"" + id
				+ "\",\"date_time\":\"" + dateTime
				+ "\",\"body\":\"" + body
				+ "\"}";
	}

	public JmsMessage() {
	}

	public JmsMessage(int id, String dateTime, String body) {
		this.id = id;
		this.dateTime = dateTime;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public String getBody() {
		return body;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
