package tech.bergen.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import tech.bergen._Rand;
import tech.bergen.model.serial.Queue;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    void serializeDe(){
        String body = null;
        String name = randomAlphabetic(4);
        int size = -1;
        try {
            Queue queueBefore = new Queue(name, new _Rand()._messageSome(1, 3));
            size = queueBefore.getMassages().size();
            ObjectMapper mapper = new ObjectMapper();
//            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            body = mapper.writeValueAsString(queueBefore);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Queue queueAfter = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            queueAfter = mapper.readValue(body, Queue.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertNotNull(queueAfter);
        assertEquals(name, queueAfter.getName());
        assertEquals(size, queueAfter.getMassages().size());
    }

}