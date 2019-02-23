package com.guillem.gvilachess.model.domain.chess.pieces;

import com.guillem.gvilachess.model.domain.chess.GameState;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.PieceTypeConfiguration;
import com.guillem.gvilachess.model.domain.chess.enums.Position;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guillem on 13/02/2019.
 */
public class Rook extends Piece implements Serializable {

    private static final long serialVersionUID = 8332798544326999468L;

    public Rook(Color color) {
        super(color, PieceTypeConfiguration.ROOK);
    }

}
