package com.guillem.gvilachess.model.domain.chess.movements;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.PieceTypeConfiguration;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Bishop;
import com.guillem.gvilachess.model.domain.chess.pieces.King;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
import com.guillem.gvilachess.model.domain.chess.pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Created by guillem on 19/02/2019.
 */
public class DiagonalMovementTest {

    BoardData boardData;

    @Before
    public void setUp() {
        boardData = new BoardData();
        boardData.initPieces();
    }

    @Test
    public void isValidDiagonalMovement() {
        Position position = Position.A4;
        Piece bishop = new Bishop(Color.WHITE);

        List<Move> movements = new DiagonalMovement().getAllMovements(position, boardData, bishop.getColor());

        List<Move> expectedList = Stream.of(
                        new Move(Position.A4, Position.B5),
                        new Move(Position.A4, Position.C6),
                        new Move(Position.A4, Position.B3),
                        new Move(Position.A4, Position.D7)
                        ).collect(Collectors.toList());

        assertThat("Movement diagonal invalid",
                movements, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void isValidDiagonalMovementWithPiece() {
        Position position = Position.G4;
        Piece queen = new Queen(Color.WHITE);

        boardData.addPiece(new King(Color.BLACK), Position.F5);

        List<Move> movements = new DiagonalMovement().getAllMovements(position, boardData, queen.getColor());

        List<Move> expectedList = Stream.of(
                new Move(Position.G4, Position.H5),
                new Move(Position.G4, Position.H3),
                new Move(Position.G4, Position.F3),
                new Move(Position.G4, Position.F5)
        ).collect(Collectors.toList());

        assertThat("Movement diagonal invalid",
                movements, containsInAnyOrder(expectedList.toArray()));
    }

}