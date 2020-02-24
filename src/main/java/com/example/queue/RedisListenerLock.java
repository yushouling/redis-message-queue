package com.example.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

/**
 * 使用锁解决多个消费者问题，未验证通过
 */
@Component
@Slf4j
public class RedisListenerLock implements MessageListener {
    @Resource
    private RedisLockRegistry redisLockRegistry;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Lock lock = redisLockRegistry.obtain("demo-lock");
        lock.lock(); //上锁
        try {
            log.info("从消息通道={}监听到消息", new String(pattern));
            log.info("从消息通道={}监听到消息", new String(message.getChannel()));
            log.info("元消息={}", new String(message.getBody()));
            // 新建一个用于反序列化的对象，注意这里的对象要和前面配置的一样
            // 因为我前面设置的默认序列化方式为GenericJackson2Jso�nRedisSerializer
            // 所以这里的实现方式为GenericJackson2JsonRedisSerializer
            RedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
            log.info("反序列化后的消息={}", serializer.deserialize(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //解锁
        }
    }
}