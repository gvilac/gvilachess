package com.guillem.gvilachess.model.domain.chess.movements;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Bishop;
import com.guillem.gvilachess.model.domain.chess.pieces.King;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
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
public class KingMovementTest {

    BoardData boardData;

    @Before
    public void setUp() {
        boardData = new BoardData();
        boardData.initPieces();
    }

    @Test
    public void isValidKingMovement() {
        Position position = Position.A4;
        Piece king = new King(Color.WHITE);

        List<Move> movements = new DiagonalMovement().getAllMovements(position, boardData, king.getColor());

        assertTrue(false);
    }

}