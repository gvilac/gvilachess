package com.guillem.gvilachess.model.domain.chess.enums;

import com.guillem.gvilachess.model.domain.chess.movements.DiagonalMovement;
import com.guillem.gvilachess.model.domain.chess.movements.KingMovement;
import com.guillem.gvilachess.model.domain.chess.movements.RookMovement;
import com.guillem.gvilachess.model.domain.chess.movements.KnightMovement;
import com.guillem.gvilachess.model.domain.chess.movements.Movement;
import com.guillem.gvilachess.model.domain.chess.movements.PawnMovement;

import java.util.Arrays;
import java.util.List;

public enum PieceTypeConfiguration {

    KING("King", "K", new KingMovement()),
    QUEEN("Queen", "Q", new RookMovement(), new DiagonalMovement()),
    ROOK("Rook", "R", new RookMovement()),
    BISHOP("Bishop", "B", new DiagonalMovement()),
    KNIGHT("Knight", "N", new KnightMovement()),
    PAWN("Pawn", "P", new PawnMovement());

    private final String description;
    private final String code;
    private List<Movement> movementsTypes;

    PieceTypeConfiguration(String description, String code, Movement... movementsTypes) {
        this.description = description;
        this.code = code;
        this.movementsTypes = Arrays.asList(movementsTypes);
    }

    public List<Movement> getAllMovementsTypes() {
        return this.movementsTypes;
    }

    public String getCode() {
        return this.code;
    }

}
