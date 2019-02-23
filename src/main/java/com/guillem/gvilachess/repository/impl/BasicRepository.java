package com.guillem.gvilachess.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by guillem on 14/02/2019.
 */
public class BasicRepository {

    protected RedisTemplate<String, Object> redisTemplate;
    protected HashOperations hashOperations;

    public BasicRepository(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
}
