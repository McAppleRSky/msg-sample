package tech.bergen._2_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tech.bergen.model.HttpMessage;

@Service
public class DispatcherService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jms.queue}")
    String jmsQueue;

    public void sendMessage(String message){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpMessage httpMessage = null;
        String parentMessage = null, queueName = null;
        try {
            httpMessage = objectMapper.readValue(message, HttpMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        parentMessage = httpMessage.toStringParent();
        queueName = httpMessage.getQueueName();
        jmsTemplate.convertAndSend(queueName, parentMessage);
    }

}
