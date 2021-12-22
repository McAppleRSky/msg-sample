package tech.bergen._2_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.bergen.model.serial.Queue;

import java.sql.Timestamp;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiverService {

//    @Autowired
    private final JmsTemplate jmsTemplate;

    private final MessageStoreService messageStoreService;

    @Value("${jms.queue}")
    String jmsQueue;

    @JmsListener(destination = "${jms.queue}")
    public void receiveMessage(String messageReceived){
        log.info("Received message: " + messageReceived);

        ObjectMapper objectMapper = new ObjectMapper();
        tech.bergen.model.serial.Message message = null;
        try {
            message = objectMapper.readValue(messageReceived, tech.bergen.model.serial.Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        messageStoreService.create(
                    new tech.bergen.model.persist.Message(
                            0,
                            message.getId(),
                            Timestamp.valueOf(message.getDateTime()),
                            message.getBody()
                    )
            );
    }

    // https://habr.com/ru/post/352954/
//    private static final String CRON = "*/10 * * * * *";
    /* @Scheduled(cron = CRON)
    public void sendMailToUsers() {
        log.info("Receiving message ...");
        String queueName = jmsQueue;
        jmsTemplate.receiveAndConvert(queueName);}*/

}
