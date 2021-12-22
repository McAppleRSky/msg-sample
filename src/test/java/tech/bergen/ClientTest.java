package tech.bergen;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import tech.bergen.model.serial.Queue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("deprecation")
class ClientTest {

	private static final String HOST = "http://localhost:8080";
	private static final String RES = "/api/0.0.1/msg";
	private static final String POSTS_API_URL = HOST + RES;

	private static final int WAIT_SPRING_UP = 30_000;

	private static final String QUEUE_NAME = "Q.Test";

	@Test
	public void main_test() {
		Thread server = new Thread(() -> Main.main(new String[]{}));
		server.start();
		try {
			Thread.sleep(WAIT_SPRING_UP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HttpClient client = HttpClient.newHttpClient();

		Queue queue = new Queue(QUEUE_NAME, new _Rand()._messageSome(1, 10));
		int messageCount = queue.getMassages().size();
		ObjectMapper mapper = new ObjectMapper();
		String body = null;
//		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			body = mapper.writeValueAsString(queue);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.header("content-type", "application/json")
				.uri(URI.create(POSTS_API_URL))
				.build();
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (java.net.ConnectException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNotNull(response);
		int EXPECTED_HTTP_STATUS = 200;
		assertEquals(EXPECTED_HTTP_STATUS, response.statusCode());
		String responseBody = response.body();
		server.stop(); // deprecated
	}

}
