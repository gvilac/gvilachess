package com.guillem.gvilachess.model.domain.chess;

import com.guillem.gvilachess.model.domain.chess.pieces.Piece;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by guillem on 22/02/2019.
 */
@AllArgsConstructor
@Getter
@Builder
public class HistoricMovement implements Serializable {

    private static final long serialVersionUID = 6779006092374539047L;

    private Move move;
    private Piece piece;

}
