package com.guillem.gvilachess.model.domain.chess.pieces;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.PieceTypeConfiguration;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.movements.Movement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public abstract class Piece implements Serializable {

    private static final long serialVersionUID = 2437150728081546053L;

    protected Color color;
    protected PieceTypeConfiguration pieceType;

    public boolean isValidMove(Move move, BoardData boardData) {
        List<Movement> movements = pieceType.getAllMovementsTypes();
        return composeMovements(movements, move.getFrom(), boardData).contains(move);
    }

    public List<Move> getAllValidMovements(Position position, BoardData boardData) {
        List<Movement> movements = pieceType.getAllMovementsTypes();
        return composeMovements(movements, position, boardData);
    }

    private List<Move> composeMovements(List<Movement> movements, Position position, BoardData boardData) {
        List<Move> possibleMoves = new ArrayList<>();
        movements.forEach(movement -> possibleMoves.addAll(movement.getAllMovements(position, boardData, color)));
        return possibleMoves;
    }
}
