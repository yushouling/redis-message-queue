package com.example.queue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 消息发送测试
 */
@SpringBootTest
class RedisMessageQueueApplicationTests {

    @Autowired
    Publisher publisher;

    @Test
    void sendMessage() {
        publisher.publish("This is a redis message queue.");
    }

}
