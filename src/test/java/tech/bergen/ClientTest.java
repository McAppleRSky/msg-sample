package tech.bergen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class ClientTest {

	@Test
	public void main() {
		Thread server = new Thread(() -> Main.main(new String[]{}));
		server.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		server.stop();
	}

}
