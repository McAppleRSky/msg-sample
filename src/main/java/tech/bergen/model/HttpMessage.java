package tech.bergen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class HttpMessage extends JmsMessage {

	@JsonProperty("queue_name")
	private String queueName;

	@Override
	public String toString() {
		return "{\"id\":\"" + this.getId()
				+ "\",\"date_time\":\"" + this.getDateTime()
				+ "\",\"body\":\"" + this.getBody()
				+ "\",\"queue_name\":\"" + queueName
				+ "\"}";
	}
	public String toStringParent() {
		return super.toString();
	}

	public HttpMessage() {
	}

	public HttpMessage(int id, String dateTime, String body, String queueName) {
		super(id, dateTime, body);
		this.queueName = queueName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
