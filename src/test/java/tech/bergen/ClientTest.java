package tech.bergen;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import tech.bergen.model.HttpMessage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.time.LocalDateTime.now;

class ClientTest {

	private static final String HOST = "http://localhost:8080";
	private static final String RES = "/api/0.0.1/msg";
	private static final String POSTS_API_URL = HOST + RES;

	private static final String MESSAGE = new HttpMessage(0, now().format(tech.bergen.Main.getDateTimeFormatter()), "body", "queue0").toString();

	@Test
	public void main_test() {
		Thread server = new Thread(() -> Main.main(new String[]{}));
		server.start();
		try {
			Thread.sleep(30_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("msg to : " + POSTS_API_URL);
		HttpClient client = HttpClient.newHttpClient();
		// https://stackoverflow.com/questions/7181534/http-post-using-json-in-java
		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(MESSAGE))
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
		if (response != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules();
			List<HttpMessage> posts = null;
			try {
				posts = mapper.readValue(response.body(), new TypeReference<List<HttpMessage>>() {});
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			posts.forEach(System.out::println);
		}

		server.stop();
	}

}
