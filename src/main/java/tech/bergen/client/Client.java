package tech.bergen.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Client {
	
	private static final String HOST = "http://localhost:8080";
	private static final String RES = "/api/0.0.1/msg";
	private static final String POSTS_API_URL = HOST + RES;

	private static final String payload = "{\"name\": \"myname\", \"age\": \"20\"}";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("msg to : " + POSTS_API_URL);
		HttpClient client = HttpClient.newHttpClient();
		// https://stackoverflow.com/questions/7181534/http-post-using-json-in-java
		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(payload))
				.header("content-type", "application/json")
				.uri(URI.create(POSTS_API_URL))
				.build();
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (java.net.ConnectException e) {
			System.out.println(e.getMessage());
		}
		if (response != null) {
			ObjectMapper mapper = new ObjectMapper();
			List<Msg> posts = mapper.readValue(response.body(), new TypeReference<List<Msg>>() {});
			posts.forEach(System.out::println);
		}
	}
}