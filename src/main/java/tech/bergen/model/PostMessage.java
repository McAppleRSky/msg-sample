package tech.bergen.model;

import java.time.LocalDateTime;

public class PostMessage extends JmsMessage {

	private String queueName;

	@Override
	public String toString() {
		return "{\"id\":\"" + id
				+ "\",\"dtime\":\"" + dateTime.format(tech.bergen.Main.getDateTimeFormatter())
				+ "\",\"body\":\"" + body
				+ "\",\"qname\":\"" + queueName
				+ "\"}";
	}

	public PostMessage() {
	}

	public PostMessage(int id, LocalDateTime dateTime, String body, String queueName) {
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
