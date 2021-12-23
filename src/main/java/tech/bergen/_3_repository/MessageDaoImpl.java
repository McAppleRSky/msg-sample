package tech.bergen._3_repository;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.bergen.model.persist.Message;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MessageDaoImpl implements MessageDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Nullable
    @Override
    public Message findById(@Nonnull Integer id) {
        // TODO: need impl
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public List<Message> findAll() {
        // TODO: need impl
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public Message update(@Nonnull Message person) {
        // TODO: need impl
        throw new NotImplementedException();
    }

    @Nullable
    @Override
    public Message delete(@Nonnull Integer id) {
        // TODO: need impl
        throw new NotImplementedException();
    }

    @Override
    public Message save(Message message) {
        entityManager.persist(message);
        return message;
    }

}
