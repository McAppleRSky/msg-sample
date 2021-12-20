package tech.bergen.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import tech.bergen.Main;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    // https://www.baeldung.com/jackson-object-mapper-tutorial
    @Test
    void testJacksonJmsTstObj() {
        String value1 = "Black", value2 = "BMW";
        String json = "{ \"color\" : \"" + value1 + "\", \"type\" : \"" + value2 + "\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        TestModel model = null;
        try {
            model = objectMapper.readValue(json, TestModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertNotNull(model);
        assertEquals(TestModel.class, model.getClass());
        assertEquals(value1, model.getColor());
        assertEquals(value2, model.getType());
    }

    @Test
    void testJacksonJmsMessage(){
        int id = nextInt();
        LocalDateTime now = LocalDateTime.now();
        String body = randomAlphabetic(10);
        String message = new JmsMessage(id, now, body).toString();
        ObjectMapper objectMapper = new ObjectMapper();
        JmsMessage jmsMessage = null;
        try {
            jmsMessage = objectMapper.readValue(message, JmsMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    @Test
    void testJmsToString() {
        LocalDateTime time = LocalDateTime.now();
        int id = nextInt();
        String body = randomAlphabetic(10), expected;
        JmsMessage actualMessage = new JmsMessage(id, time, body);
        expected = String.format(
                "{"     + "\"id\":\"%d\""
                        + ",\"dtime\":\"%s\""
                        + ",\"body\":\"%s\""
                        + '}',
                id,
                time.format(tech.bergen.Main.getDateTimeFormatter()),
                body);
        assertEquals(expected, actualMessage.toString());
    }

    @Test
    void testPostToString() {
        LocalDateTime time = LocalDateTime.now();
        int id = nextInt();
        String body = randomAlphabetic(10),
                name = randomAlphabetic(4), expected;
        PostMessage actualMessage = new PostMessage(id, time, body, name);
        expected = String.format(
                "{"     + "\"id\":\"%d\""
                        + ",\"dtime\":\"%s\""
                        + ",\"body\":\"%s\""
                        + ",\"qname\":\"%s\""
                        + '}',
                id,
                time.format(tech.bergen.Main.getDateTimeFormatter()),
                body, name);
        assertEquals(expected, actualMessage.toString());
    }

}
