package com.guillem.gvilachess.model.domain.chess;


import com.guillem.gvilachess.model.domain.chess.enums.MovementType;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class Move implements Serializable {

    private static final long serialVersionUID = -7785766517154693475L;
    private Position from;
    private Position to;

    private MovementType movementType;
    private Piece pieceAffected;

    public Move(Position from, Position to) {
        this(from, to, MovementType.NORMAL, null);
    }

}
