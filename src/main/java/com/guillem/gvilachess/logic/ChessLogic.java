package com.guillem.gvilachess.logic;

import com.guillem.gvilachess.exceptions.NotValidMovementException;
import com.guillem.gvilachess.exceptions.PieceNotFoundException;
import com.guillem.gvilachess.exceptions.PositionNotFoundException;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.GameState;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
import com.guillem.gvilachess.model.domain.user.Account;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChessLogic {

    private GameState gameState;

    public ChessLogic(GameState gameState) {
        this.gameState = gameState;
    }

    public void handlePlay(Move move, Account account) throws PieceNotFoundException, NotValidMovementException {
        BoardData boardData = gameState.getBoardData();
        Color playersColor = gameState.getColorByUsername(account.getUsername());

        verifyCurrentPlayerTurn(playersColor);
        Move moveComposed = verifyIsValidMovement(move, boardData, playersColor);

        boardData.movePiece(moveComposed);

        boardData.updateBoardDataPostMovement();

        if (isCheckMate(boardData, playersColor)) {
            //TODO: handle finish game
            System.out.println("Check mate! Player " + account.getUsername() + " wins");
        } else {
            gameState.updateGameValuesPostTurn();
        }

        printChessDebug(boardData);
    }

    private Move verifyIsValidMovement(Move move, BoardData boardData, Color playersColor) throws PieceNotFoundException, NotValidMovementException {
        Piece pieceToMove = boardData.getPieceFromPosition(move.getFrom());

        if (!playersColor.equals(pieceToMove.getColor())) {
            throw new NotValidMovementException("Player cannot move a piece from the other player");
        }

        Move futureMove = pieceToMove.getAllValidMovements(move.getFrom(), boardData).stream()
                .filter((moveComposed) -> moveComposed.getFrom().equals(move.getFrom()) && moveComposed.getTo().equals(move.getTo()))
                .findFirst()
                .orElseThrow(() -> new NotValidMovementException("Movement " + move + " not valid."));

        verifyMovementIsFutureChecked(boardData, playersColor, futureMove);

        return futureMove;
    }

    private void verifyMovementIsFutureChecked(BoardData boardData, Color playersColor, Move futureMove) throws NotValidMovementException {
        Map<Position, Piece> mockupPiecesAfterMovement = boardData.doMockupMovement(futureMove);
        Optional<Piece> possiblePieceAttackingKing = boardData.isPlayerCheckedAfterPossibleMovement(mockupPiecesAfterMovement, playersColor);
        if (possiblePieceAttackingKing.isPresent()) {
            throw new NotValidMovementException("Player cannot do this movement, your king is checked by " + possiblePieceAttackingKing.get().toString());
        }
    }

    private boolean isCheckMate(BoardData boardData, Color playersColor) throws NotValidMovementException {
        List<Move> possibleEnemyMovements = boardData.getAllBasicMovementsFromColor(Color.getOtherPlayerColor(playersColor));
        for (Move movement : possibleEnemyMovements) {
            Map<Position, Piece> mockupPiecesAfterMovement = boardData.doMockupMovement(movement);
            Optional<Piece> possiblePieceAttackingKing = boardData.isPlayerCheckedAfterPossibleMovement(mockupPiecesAfterMovement, Color.getOtherPlayerColor(playersColor));
            if (!possiblePieceAttackingKing.isPresent()) {
                return false;
            }
        }
        return true;
    }

    private void verifyCurrentPlayerTurn(Color playersColor) throws NotValidMovementException {
        if (!gameState.getCurrentPlayersTurn().equals(playersColor)) {
            throw new NotValidMovementException("Not current players turn.");
        }
    }

    public void printChessDebug(BoardData boardData) {
        try {
            for (int row = BoardData.BOARD_SIZE - 1; row >= 0; row--) {
                System.out.println("");
                System.out.println("---------------------------------------------------------");

                for (int column = 0; column < BoardData.BOARD_SIZE; column++) {
                    Optional<Piece> piece = boardData.getPieceFromCoordinate(column, row);
                    if (piece.isPresent()) {
                        System.out.print("| " + " " + piece.get().getColor().getCode() + piece.get().getPieceType().getCode() + " " + " ");
                    } else {
                        System.out.print("|      ");
                    }
                }
                System.out.print("|");
            }
            System.out.println("");
            System.out.println("---------------------------------------------------------");
        } catch (PositionNotFoundException e) {
            e.printStackTrace();
        } catch (PieceNotFoundException e) {
            e.printStackTrace();
        }
    }

}
