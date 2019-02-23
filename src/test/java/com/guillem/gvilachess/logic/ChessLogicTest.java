package com.guillem.gvilachess.logic;

import com.guillem.gvilachess.exceptions.NotValidMovementException;
import com.guillem.gvilachess.exceptions.PieceNotFoundException;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.GameState;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.user.Account;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guillem on 20/02/2019.
 */
public class ChessLogicTest {


    ChessLogic chessLogic;

    GameState gameState;

    @Before
    public void setUp() {
        gameState = new GameState("test" , "black");
        gameState.initGame();
        chessLogic = new ChessLogic(gameState);
    }

    @Test
    public void testPrint() throws PieceNotFoundException, NotValidMovementException {
        chessLogic.printChessDebug(gameState.getBoardData());

        Account acc = new Account();
        acc.setUsername("test");
        chessLogic.handlePlay(new Move(Position.E2,  Position.E3), acc);
    }

}