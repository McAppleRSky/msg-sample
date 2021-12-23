package tech.bergen.model;

import java.sql.Timestamp;

public class MessageHelper {

    public static tech.bergen.model.persist.Message messageSerialPersist(tech.bergen.model.serial.Message message){
        return new tech.bergen.model.persist.Message(
                0,
                message.getId(),
                Timestamp.valueOf(message.getDateTime()),
                message.getBody()
        );
    }

}
