package tech.bergen._2_service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;

@Service
public class ReceiverService {

    //    Logger log = LoggerFactory.getLogger(ReceiverService.class);
    private static final Logger logger = tech.bergen.Main.configureLog4j(ReceiverService.class);

    @JmsListener(destination = "${jms.queue}")
    public void receiveMessage(String message){
        logger.info("Received message: " + message);
    }
}
