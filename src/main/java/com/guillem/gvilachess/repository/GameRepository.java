package com.guillem.gvilachess.repository;

import com.guillem.gvilachess.exceptions.GameNotFoundException;
import com.guillem.gvilachess.model.domain.chess.GameState;

/**
 * Created by guillem on 14/02/2019.
 */
public interface GameRepository {

    public void add(GameState gameState, String id);

    public GameState getGameState(String id) throws GameNotFoundException;

}
