package tech.bergen.model.serial;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private int id;
    @JsonProperty("date_time")
    private String dateTime;
    private String body;

}
