package com.example.queue;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 发送消息
 */
@Service
public class Publisher{
    @Resource
    private RedisTemplate<String, Object> redis;

    public void publish(Object msg){
        redis.convertAndSend("demo-channel",msg);
    }
}