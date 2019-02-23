package com.guillem.gvilachess.model.domain.chess;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guillem.gvilachess.exceptions.PieceNotFoundException;
import com.guillem.gvilachess.exceptions.PositionNotFoundException;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Bishop;
import com.guillem.gvilachess.model.domain.chess.pieces.King;
import com.guillem.gvilachess.model.domain.chess.pieces.Knight;
import com.guillem.gvilachess.model.domain.chess.pieces.Pawn;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
import com.guillem.gvilachess.model.domain.chess.pieces.Queen;
import com.guillem.gvilachess.model.domain.chess.pieces.Rook;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameState implements Serializable {

    private static final long serialVersionUID = 1870649422239890097L;

    private BoardData boardData;
    private String usernameWhite;
    private String usernameBlack;

    private Color currentPlayersTurn;
    private boolean gameFinished;

    public GameState(String usernameWhite, String usernameBlack) {
        this.usernameBlack = usernameBlack;
        this.usernameWhite = usernameWhite;
    }

    public void initGame() {
        boardData = new BoardData();
        boardData.initPieces();
        this.currentPlayersTurn = Color.WHITE;
        gameFinished = false;
    }

    public Color getColorByUsername(String username) {
        return username.equals(usernameWhite) ? Color.WHITE : Color.BLACK;
    }

    public void updateGameValuesPostTurn() {
        this.currentPlayersTurn = Color.getOtherPlayerColor(currentPlayersTurn);
    }
}
