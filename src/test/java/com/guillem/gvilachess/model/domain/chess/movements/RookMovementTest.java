package com.guillem.gvilachess.model.domain.chess.movements;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
import com.guillem.gvilachess.model.domain.chess.pieces.Queen;
import com.guillem.gvilachess.model.domain.chess.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Created by guillem on 19/02/2019.
 */
public class RookMovementTest {

    BoardData boardData;

    @Before
    public void setUp() {
        boardData = new BoardData();
        boardData.initPieces();
    }

    @Test
    public void isValidRookMovement() {
        Position position = Position.A4;
        Piece rook = new Rook(Color.BLACK);

        List<Move> movements = new RookMovement().getAllMovements(position, boardData, rook.getColor());

        List<Move> expectedList = Stream.of(
                new Move(Position.A4, Position.A5),
                new Move(Position.A4, Position.A6),
                new Move(Position.A4, Position.B4),
                new Move(Position.A4, Position.C4),
                new Move(Position.A4, Position.D4),
                new Move(Position.A4, Position.E4),
                new Move(Position.A4, Position.F4),
                new Move(Position.A4, Position.G4),
                new Move(Position.A4, Position.H4),
                new Move(Position.A4, Position.A3),
                new Move(Position.A4, Position.A2)

                ).collect(Collectors.toList());

        assertThat("Movement rook invalid",
                movements, containsInAnyOrder(expectedList.toArray()));
    }
}