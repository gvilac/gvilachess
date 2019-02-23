package com.guillem.gvilachess.model.domain.chess.movements;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.MovementType;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by guillem on 19/02/2019.
 */
public class MovementUtils {

    static List<Move> buildMovements(Position initialPosition, Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> finalPositions) {
        List<Move> movements = new ArrayList<>();
        finalPositions.forEach((position, moveType) -> movements.add(new Move(initialPosition, position, moveType.getKey(), moveType.getValue())));
        return movements;
    }

    static Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> getOneDirectionMovements(Position position, BoardData boardData, Color color, Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> possibleNewPositions, int addingToX, int addingToY, int deep) {
        if (deep == 0) {
            return possibleNewPositions;
        }
        Optional<Position> newPosition = Position.valueOf(position.getX() + addingToX, position.getY() + addingToY);
        if (newPosition.isPresent()) {
            Optional<Piece> pieceInNewPosition = boardData.getPieceFromPositionOptional(newPosition.get());
            if (pieceInNewPosition.isPresent()) {
                if (!pieceInNewPosition.get().getColor().equals(color)) {
                    possibleNewPositions.put(newPosition.get(), new AbstractMap.SimpleEntry<>(MovementType.ATTACK, pieceInNewPosition.get()));
                }
            } else {
                possibleNewPositions.put(newPosition.get(), new AbstractMap.SimpleEntry<>(MovementType.NORMAL, null));
                return getOneDirectionMovements(newPosition.get(), boardData, color, possibleNewPositions, addingToX, addingToY, deep - 1);
            }
        }
        return possibleNewPositions;
    }
}
