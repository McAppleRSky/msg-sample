package tech.bergen._2_service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import tech.bergen._3_repository.MessageDao;
import tech.bergen.model.persist.Message;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageStoreServiceImpl implements MessageStoreService{

    private final MessageDao messageDao;

    @Nonnull
    @Override
    public List<Message> findAll() {
        // TODO: need impl
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public Message getById(int id) {
        // TODO: need impl
        throw new NotImplementedException();
    }

    @Override
    public int create(@Nonnull Message message) {
        Message messagePersisted = null;
        messagePersisted = messageDao.save(message);
        return messagePersisted==null ? -1 : 1;
    }

    @Nonnull
    @Override
    public Message update(int id, @Nonnull Message request) {
        // TODO: need impl
        throw new NotImplementedException();
    }

    @Override
    public void delete(int id) {
        // TODO: need impl
        throw new NotImplementedException();
    }

}
