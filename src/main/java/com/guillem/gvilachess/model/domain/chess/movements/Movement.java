package com.guillem.gvilachess.model.domain.chess.movements;

import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.BoardData;
import com.guillem.gvilachess.model.domain.chess.Move;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.Position;
import com.guillem.gvilachess.model.domain.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillem on 17/02/2019.
 */
public interface Movement {

    public List<Move> getAllMovements(Position position, BoardData boardData, Color color);


}
