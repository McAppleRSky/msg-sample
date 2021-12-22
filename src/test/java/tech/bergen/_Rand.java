package tech.bergen;

import tech.bergen.model.Message;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

@SuppressWarnings("unchecked")
public class _Rand {

    private static int BODY_LENGTH = 10;

    public int _int (int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public int _intMin(int min, int max) {
        int _max = _int(min, max);
        return min + (int)(Math.random() * ((_max - min) + 1));
    }

    public static Message _message(){
        return new Message(nextInt(), now().format(Main.getDateTimeFormatter()), randomAlphabetic(BODY_LENGTH));
    }

    public List<Message> _messageSome(int min, int max){
        List<Message> result = new ArrayList();
        for (int i = 0; i < _intMin(min, max); i++) {
            result.add(_message());
        }
        return result;
    }

}
