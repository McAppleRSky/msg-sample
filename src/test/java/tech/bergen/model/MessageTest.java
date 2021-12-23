package tech.bergen.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    // https://www.baeldung.com/jackson-object-mapper-tutorial
    void testJacksonJmsTstObj() {
        String value1 = "Black", value2 = "BMW";
        String json = "{ \"color\" : \"" + value1 + "\", \"type\" : \"" + value2 + "\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        _TestedModel model = null;
        try {
            model = objectMapper.readValue(json, _TestedModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertNotNull(model);
        assertEquals(_TestedModel.class, model.getClass());
        assertEquals(value1, model.getColor());
        assertEquals(value2, model.getType());
    }

    /*@Test
    void testJacksonHttpMessage(){
        int id = nextInt();
        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(tech.bergen.Main.getDateTimeFormatter());
        String body = randomAlphabetic(10);
        String name = randomAlphabetic(4);
        String message = new HttpMessage(id, dateTime, body, name).toString();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpMessage httpMessage = null;
        try {
            httpMessage = objectMapper.readValue(message, HttpMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertNotNull(httpMessage);
        assertEquals(id, httpMessage.getId());
        assertEquals(dateTime, httpMessage.getDateTime());
        assertEquals(body, httpMessage.getBody());
        assertEquals(name, httpMessage.getQueueName());
    }

    @Test
    void testJacksonJmsMessage(){
        int id = nextInt();
        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(tech.bergen.Main.getDateTimeFormatter());
        String body = randomAlphabetic(10);
        String message = new JmsMessage(id, dateTime, body).toString();
        ObjectMapper objectMapper = new ObjectMapper();
        JmsMessage jmsMessage = null;
        try {
            jmsMessage = objectMapper.readValue(message, JmsMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertNotNull(jmsMessage);
        assertEquals(id, jmsMessage.getId());
        assertEquals(dateTime, jmsMessage.getDateTime());
        assertEquals(body, jmsMessage.getBody());
    }

    @Test
    void testJmsToString() {
        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(tech.bergen.Main.getDateTimeFormatter());
        int id = nextInt();
        String body = randomAlphabetic(10), expected;
        JmsMessage actualMessage = new JmsMessage(id, dateTime, body);
        expected = String.format(
                "{"     + "\"id\":\"%d\""
                        + ",\"date_time\":\"%s\""
                        + ",\"body\":\"%s\""
                        + '}',
                id, dateTime, body);
        assertEquals(expected, actualMessage.toString());
    }

    @Test
    void testPostToString() {
        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(tech.bergen.Main.getDateTimeFormatter());
        int id = nextInt();
        String body = randomAlphabetic(10),
                name = randomAlphabetic(4), expected;
        HttpMessage actualMessage = new HttpMessage(id, dateTime, body, name);
        expected = String.format(
                "{"     + "\"id\":\"%d\""
                        + ",\"date_time\":\"%s\""
                        + ",\"body\":\"%s\""
                        + ",\"qname\":\"%s\""
                        + '}', id, dateTime, body, name);
        assertEquals(expected, actualMessage.toString());
    }*/

}
