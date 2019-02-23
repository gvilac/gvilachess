package com.guillem.gvilachess.model.domain.chess;

import com.guillem.gvilachess.exceptions.PieceNotFoundException;
import com.guillem.gvilachess.exceptions.PositionNotFoundException;
import com.guillem.gvilachess.model.domain.chess.GameState;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.PieceTypeConfiguration;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by guillem on 13/02/2019.
 */
public class BoardDataTest {

    private BoardData boardData;

    @Before
    public void setUp() {
        boardData = new BoardData();
        boardData.initPieces();
    }

    @Test
    public void getPieceFromPositionFound() throws Exception {
        Piece rook = boardData.getPieceFromPosition(Position.A1);
        Piece knight = boardData.getPieceFromPosition(Position.B1);
        Piece bishop = boardData.getPieceFromPosition(Position.C1);
        Piece queen = boardData.getPieceFromPosition(Position.D1);
        Piece king = boardData.getPieceFromPosition(Position.E1);
        Piece pawn = boardData.getPieceFromPosition(Position.A2);

        assertEquals(rook.getPieceType(), PieceTypeConfiguration.ROOK);
        assertEquals(knight.getPieceType(), PieceTypeConfiguration.KNIGHT);
        assertEquals(bishop.getPieceType(), PieceTypeConfiguration.BISHOP);
        assertEquals(queen.getPieceType(), PieceTypeConfiguration.QUEEN);
        assertEquals(king.getPieceType(), PieceTypeConfiguration.KING);
        assertEquals(pawn.getPieceType(), PieceTypeConfiguration.PAWN);
    }

    @Test(expected = PieceNotFoundException.class)
    public void getPieceFromPositionNotFound() throws Exception {
        boardData.getPieceFromPosition(Position.C5);
    }

    @Test
    public void getPieceFromCoordinate() throws Exception {
        Optional<Piece> rook = boardData.getPieceFromCoordinate(0, 0);
        Optional<Piece> knight = boardData.getPieceFromCoordinate(1, 0);
        Optional<Piece> bishop = boardData.getPieceFromCoordinate(2, 0);
        Optional<Piece> queen = boardData.getPieceFromCoordinate(3, 0);
        Optional<Piece> king = boardData.getPieceFromCoordinate(4, 0);
        Optional<Piece> pawn = boardData.getPieceFromCoordinate(0, 1);

        assertEquals(rook.get().getPieceType(), PieceTypeConfiguration.ROOK);
        assertEquals(knight.get().getPieceType(), PieceTypeConfiguration.KNIGHT);
        assertEquals(bishop.get().getPieceType(), PieceTypeConfiguration.BISHOP);
        assertEquals(queen.get().getPieceType(), PieceTypeConfiguration.QUEEN);
        assertEquals(king.get().getPieceType(), PieceTypeConfiguration.KING);
        assertEquals(pawn.get().getPieceType(), PieceTypeConfiguration.PAWN);
    }

    @Test(expected = PieceNotFoundException.class)
    public void getPieceFromCoordinateNotFound() throws Exception {
        boardData.getPieceFromCoordinate(5, 5);
    }

    @Test(expected = PositionNotFoundException.class)
    public void getPieceFromCoordinatePositionNotExist() throws Exception {
        boardData.getPieceFromCoordinate(8, 8);
    }

    @Test
    public void movePiece() throws Exception {
        boardData.movePiece(new Move(Position.A1, Position.A7));

        assertTrue(boardData.getDeadPieces().size() == 1);
        assertTrue(boardData.getDeadPieces().get(0).getPieceType().equals(PieceTypeConfiguration.PAWN));
        assertTrue(boardData.getListPieces().get(Position.A7).getPieceType().equals(PieceTypeConfiguration.ROOK));
    }

}