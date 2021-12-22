package tech.bergen._2_service;

import tech.bergen.model.persist.Message;

import javax.annotation.Nonnull;
import java.util.List;

public interface MessageStoreService {

    @Nonnull
    List<Message> findAll();

    @Nonnull
    Message getById(int id);

    int create(@Nonnull Message message);

    @Nonnull
    Message update(int id, @Nonnull Message request);

    void delete(int id);

}
