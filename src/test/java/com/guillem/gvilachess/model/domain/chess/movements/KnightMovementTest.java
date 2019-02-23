package com.guillem.gvilachess.model.domain.chess.movements;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Knight;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
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
public class KnightMovementTest {

    BoardData boardData;

    @Before
    public void setUp() {
        boardData = new BoardData();
        boardData.initPieces();
    }

    @Test
    public void isValidKnightMovement() {
        Position position = Position.B5;
        Piece knight = new Knight(Color.WHITE);

        List<Move> movements = new KnightMovement().getAllMovements(position, boardData, knight.getColor());

        List<Move> expectedList = Stream.of(
                new Move(Position.B5, Position.A7),
                new Move(Position.B5, Position.C7),
                new Move(Position.B5, Position.D6),
                new Move(Position.B5, Position.D4),
                new Move(Position.B5, Position.C3),
                new Move(Position.B5, Position.A3)

        ).collect(Collectors.toList());

        assertThat("Movement rook invalid",
                movements, containsInAnyOrder(expectedList.toArray()));
    }

}