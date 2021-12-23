package tech.bergen._3_repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import tech.bergen._Rand;
import tech.bergen.model.MessageHelper;
import tech.bergen.model.persist.Message;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "tech.bergen")
class MessageDaoImplTest {

//    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageDao messageDao;

    void createWhenEntityManagerIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new TestEntityManager(null))
                .withMessageContaining("EntityManagerFactory must not be null");
    }

    void save_test() {
        messageDao.save(MessageHelper.messageSerialPersist(new _Rand()._message()));
    }
}