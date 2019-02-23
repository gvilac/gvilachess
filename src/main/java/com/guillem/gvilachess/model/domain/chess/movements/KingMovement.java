package com.guillem.gvilachess.model.domain.chess.movements;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.MovementType;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guillem on 19/02/2019.
 */
public class KingMovement implements Movement {


    @Override
    public List<Move> getAllMovements(Position position, BoardData boardData, Color color) {
        Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> possiblePositions = new HashMap<>();

        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), 0, 1, 1));
        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), 0, -1, 1));
        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), 1, 0, 1));
        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), -1, 0, 1));
        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), 1, -1, 1));
        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), -1, 1, 1));
        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), 1, 1, 1));
        possiblePositions.putAll(MovementUtils.getOneDirectionMovements(position, boardData, color, new HashMap<>(), -1, -1, 1));

        // add castle movement
        //TODO

        return MovementUtils.buildMovements(position, possiblePositions);
    }
}
