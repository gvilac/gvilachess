package com.guillem.gvilachess.services.impl;

import com.guillem.gvilachess.exceptions.GameNotFoundException;
import com.guillem.gvilachess.exceptions.GeneralChessLogicError;
import com.guillem.gvilachess.exceptions.NotValidMovementException;
import com.guillem.gvilachess.exceptions.PieceNotFoundException;
import com.guillem.gvilachess.logic.ChessLogic;
import com.guillem.gvilachess.model.domain.chess.GameState;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.user.Account;
import com.guillem.gvilachess.repository.AccountRepository;
import com.guillem.gvilachess.repository.GameRepository;
import com.guillem.gvilachess.services.ChessService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChessServiceImpl implements ChessService {

    private final GameRepository gameRepository;
    private final AccountRepository accountRepository;

    public ChessServiceImpl(GameRepository gameRepository, AccountRepository accountRepository) {
        this.gameRepository = gameRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void createGame(String whiteUsername, String blackUsername) throws UsernameNotFoundException {
        //TODO: user account service
        Account whiteAccount = accountRepository.findByUsername(whiteUsername);
        Account blackAccount = accountRepository.findByUsername(blackUsername);

        GameState gameState = new GameState(whiteUsername, blackUsername);
        gameState.initGame();

        String gameId = java.util.UUID.randomUUID().toString();
        gameRepository.add(gameState, gameId);

        whiteAccount.setGameId(gameId);
        blackAccount.setGameId(gameId);

        accountRepository.addAccount(whiteAccount);
        accountRepository.addAccount(blackAccount);
    }

    @Override
    public void doMovement(Move move, String username) throws GeneralChessLogicError {
        Account account = accountRepository.findByUsername(username);
        String gameId = account.getGameId();
        //TODO: protect noGameid
        GameState gameState = null;
        try {
            gameState = gameRepository.getGameState(gameId);
        } catch (GameNotFoundException e) {
            throw new GeneralChessLogicError(e);
        }

        ChessLogic logic = new ChessLogic(gameState);
        try {
            logic.handlePlay(move, account);
        } catch (PieceNotFoundException e) {
            throw new GeneralChessLogicError(e);
        } catch (NotValidMovementException e) {
            throw new GeneralChessLogicError(e);
        }

        gameRepository.add(gameState, gameId);
    }
}
