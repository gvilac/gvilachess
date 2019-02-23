package com.guillem.gvilachess.repository.impl;

import com.guillem.gvilachess.exceptions.GameNotFoundException;
import com.guillem.gvilachess.model.domain.chess.GameState;
import com.guillem.gvilachess.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by guillem on 14/02/2019.
 */
@Repository
public class GameRepositoryImpl extends BasicRepository implements GameRepository {

    private static final String KEY = "GameState";

    @Autowired
    public GameRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    public void add(GameState gameState, String id) {
        hashOperations.put(KEY, id, gameState);
    }

    public GameState getGameState(String id) throws GameNotFoundException {
        return Optional.ofNullable((GameState) hashOperations.get(KEY, id)).orElseThrow(() -> new GameNotFoundException("Game id " + id + " not found."));
    }

}
