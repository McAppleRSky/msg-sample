package tech.bergen._2_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import tech.bergen.model.JmsMessage;
import tech.bergen.model.PostMessage;

@Service
public class DispatcherService {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jms.queue}")
    String jmsQueue;

    public void sendMessage(String message){
        ObjectMapper objectMapper = new ObjectMapper();
        PostMessage postMessage = null;
        try {
            postMessage = objectMapper.readValue(message, PostMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String queueName = postMessage.getQueueName();
        JmsMessage jmsMessage = postMessage;
//        jmsTemplate.convertAndSend(jmsQueue, message);
        jmsTemplate.convertAndSend(queueName, jmsMessage);
    }
}
