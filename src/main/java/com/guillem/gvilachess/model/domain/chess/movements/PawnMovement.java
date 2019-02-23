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
import java.util.Optional;

/**
 * Created by guillem on 17/02/2019.
 */
public class PawnMovement implements Movement {

    @Override
    public List<Move> getAllMovements(Position position, BoardData boardData, Color color) {
        int direction = boardData.getMovementDirectionFromColor(color);

        Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> possiblePositions = new HashMap<>();
        possiblePositions.putAll(getPawnAdvanceMovements(position, boardData, direction, boardData.hasPieceAlreadyMoved(position, color)));
        possiblePositions.putAll(getPawnAttackMovements(position, boardData, color, direction));

        return MovementUtils.buildMovements(position, possiblePositions);
    }

    private Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> getPawnAdvanceMovements(Position position, BoardData boardData, int direction, boolean pieceAlreadyMoved) {
        Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> newPositions = new HashMap<>();
        Optional<Position> newPosition = Position.valueOf(position.getX(), position.getY() + direction);
        if (newPosition.isPresent()) {
            Optional<Piece> pieceInNewPosition = boardData.getPieceFromPositionOptional(newPosition.get());
            if (!pieceInNewPosition.isPresent()) {
                //TODO: check new position is promotion
                newPositions.put(newPosition.get(), new AbstractMap.SimpleEntry<>(MovementType.NORMAL, null));
            }
        }
        newPosition = Position.valueOf(position.getX(), position.getY() + (direction * 2));
        if (newPosition.isPresent() && !pieceAlreadyMoved) {
            Optional<Piece> pieceInNewPosition = boardData.getPieceFromPositionOptional(newPosition.get());
            if (!pieceInNewPosition.isPresent()) {
                newPositions.put(newPosition.get(), new AbstractMap.SimpleEntry<>(MovementType.DOUBLE_PAWN, null));
            }
        }
        return newPositions;
    }

    private Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> getPawnAttackMovements(Position position, BoardData boardData, Color color, int direction) {
        Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> newPositions = new HashMap<>();
        Optional<Position> newPositionLeft = Position.valueOf(position.getX() - 1, position.getY() + direction);
        Optional<Position> newPositionRight = Position.valueOf(position.getX() + 1, position.getY() + direction);

        addPossibleAttackPositions(boardData, color, newPositions, newPositionLeft);
        addPossibleAttackPositions(boardData, color, newPositions, newPositionRight);

        return newPositions;
    }

    private void addPossibleAttackPositions(BoardData boardData, Color color, Map<Position, AbstractMap.SimpleEntry<MovementType, Piece>> newPositions, Optional<Position> newPosition) {
        if (newPosition.isPresent()) {
            Optional<Piece> pieceInNewPosition = boardData.getPieceFromPositionOptional(newPosition.get());
            if (pieceInNewPosition.isPresent()) {
                if (!pieceInNewPosition.get().getColor().equals(color)) {
                    newPositions.put(newPosition.get(), new AbstractMap.SimpleEntry<>(MovementType.ATTACK, pieceInNewPosition.get()));
                }
            }
            Piece piece = boardData.isPossiblePassingAttack(newPosition.get(), color);
            if (piece != null) {
                newPositions.put(newPosition.get(), new AbstractMap.SimpleEntry<>(MovementType.ATTACK, piece));
            }
        }
    }
}
