package tech.bergen._2_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tech.bergen.model.serial.Message;
import tech.bergen.model.serial.Queue;

import java.util.List;

@Service
public class DispatcherService {

    @Autowired
    JmsTemplate jmsTemplate;

//    @Value("${jms.queue}") String jmsQueue;

    public int sendMessage(String queueMessages){
        int messageCount = -1;
        ObjectMapper objectMapper = new ObjectMapper();
        String queueName = null;
        List<Message> massages = null;
        try {
            Queue queue = objectMapper.readValue(queueMessages, Queue.class);
            queueName = queue.getName();
            massages = queue.getMassages();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        messageCount = massages.size();
        try {
            for (Message message : massages) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                String serialMessage = mapper.writeValueAsString(message);
                jmsTemplate.convertAndSend(queueName, serialMessage);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return messageCount;
    }

}
