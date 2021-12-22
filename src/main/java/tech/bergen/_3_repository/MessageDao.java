package tech.bergen._3_repository;

import org.springframework.data.repository.NoRepositoryBean;
import tech.bergen.model.persist.Message;

@NoRepositoryBean
public interface MessageDao extends CrudOperations<Message, Integer> {
    Message save(Message message);
}