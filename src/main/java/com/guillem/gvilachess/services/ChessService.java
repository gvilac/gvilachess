package com.guillem.gvilachess.services;


import com.guillem.gvilachess.exceptions.GameNotFoundException;
import com.guillem.gvilachess.exceptions.GeneralChessLogicError;
import com.guillem.gvilachess.exceptions.NotValidMovementException;
import com.guillem.gvilachess.exceptions.PieceNotFoundException;
import com.guillem.gvilachess.model.domain.chess.Move;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ChessService {

    public void createGame(String whiteUsername, String blackUsername) throws UsernameNotFoundException;

    public void doMovement(Move move, String username) throws GeneralChessLogicError;

}
