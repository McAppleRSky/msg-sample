package tech.bergen._2_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceiverService {

//    Logger log = LoggerFactory.getLogger(ReceiverService.class);

    @JmsListener(destination = "${jms.queue}")
    public void receiveMessage(String message){
        log.info("Received message: " + message);
    }
}
