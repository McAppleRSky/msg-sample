package tech.bergen.model.serial;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Queue {

    private String name;
    private List<Message> massages;

}
