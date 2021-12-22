package tech.bergen.model.persist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int id_ext;
    @Column(name="date_time",
            columnDefinition="TIMESTAMP WITHOUT TIME ZONE",
            nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreationTimestamp
    private Timestamp dateTime;
    @Column(nullable = false)
    private String body;

}
