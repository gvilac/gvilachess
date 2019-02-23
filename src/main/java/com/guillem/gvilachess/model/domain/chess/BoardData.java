package com.guillem.gvilachess.model.domain.chess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guillem.gvilachess.exceptions.PieceNotFoundException;
import com.guillem.gvilachess.exceptions.PositionNotFoundException;
import com.guillem.gvilachess.model.domain.chess.enums.Color;
import com.guillem.gvilachess.model.domain.chess.enums.MovementType;
import com.guillem.gvilachess.model.domain.chess.enums.PieceTypeConfiguration;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by guillem on 17/02/2019.
 */
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardData implements Serializable {

    public static final int BOARD_SIZE = 8;
    public static final int WHITE_DIRECTION = 1;
    public static final int BLACK_DIRECTION = -1;

    private static final long serialVersionUID = -5433456161046089148L;

    private Map<Position, Piece> listPieces;
    private List<Piece> deadPieces = new ArrayList<>();

    private List<HistoricMovement> historicBlackMovements = new ArrayList<>();
    private List<HistoricMovement> historicWhiteMovements = new ArrayList<>();

    private boolean blackCastled;
    private boolean whiteCastled;

    private boolean blackInCheck;
    private boolean whiteInCheck;

    public void initPieces() {
        listPieces = new HashMap<>();
        listPieces.put(Position.A1, new Rook(Color.WHITE));
        listPieces.put(Position.B1, new Knight(Color.WHITE));
        listPieces.put(Position.C1, new Bishop(Color.WHITE));
        listPieces.put(Position.D1, new Queen(Color.WHITE));
        listPieces.put(Position.E1, new King(Color.WHITE));
        listPieces.put(Position.F1, new Bishop(Color.WHITE));
        listPieces.put(Position.G1, new Knight(Color.WHITE));
        listPieces.put(Position.H1, new Rook(Color.WHITE));
        listPieces.put(Position.A2, new Pawn(Color.WHITE));
        listPieces.put(Position.B2, new Pawn(Color.WHITE));
        listPieces.put(Position.C2, new Pawn(Color.WHITE));
        listPieces.put(Position.D2, new Pawn(Color.WHITE));
        listPieces.put(Position.E2, new Pawn(Color.WHITE));
        listPieces.put(Position.F2, new Pawn(Color.WHITE));
        listPieces.put(Position.G2, new Pawn(Color.WHITE));
        listPieces.put(Position.H2, new Pawn(Color.WHITE));

        listPieces.put(Position.A8, new Rook(Color.BLACK));
        listPieces.put(Position.B8, new Knight(Color.BLACK));
        listPieces.put(Position.C8, new Bishop(Color.BLACK));
        listPieces.put(Position.D8, new Queen(Color.BLACK));
        listPieces.put(Position.E8, new King(Color.BLACK));
        listPieces.put(Position.F8, new Bishop(Color.BLACK));
        listPieces.put(Position.G8, new Knight(Color.BLACK));
        listPieces.put(Position.H8, new Rook(Color.BLACK));
        listPieces.put(Position.A7, new Pawn(Color.BLACK));
        listPieces.put(Position.B7, new Pawn(Color.BLACK));
        listPieces.put(Position.C7, new Pawn(Color.BLACK));
        listPieces.put(Position.D7, new Pawn(Color.BLACK));
        listPieces.put(Position.E7, new Pawn(Color.BLACK));
        listPieces.put(Position.F7, new Pawn(Color.BLACK));
        listPieces.put(Position.G7, new Pawn(Color.BLACK));
        listPieces.put(Position.H7, new Pawn(Color.BLACK));
    }

    public void initPieces(Map<Position, Piece> listPieces) {
        this.listPieces = listPieces;
    }

    public Optional<Piece> getPieceFromPositionOptional(Position position) {
        return Optional.ofNullable(listPieces.get(position));
    }

    public Piece getPieceFromPosition(Position position) throws PieceNotFoundException {
        return Optional.ofNullable(listPieces.get(position)).orElseThrow(() -> new PieceNotFoundException("No piece in position " + position.toString()));
    }

    public Optional<Piece> getPieceFromCoordinate(int x, int y) throws PositionNotFoundException, PieceNotFoundException {
        Optional<Position> position = Position.valueOf(x, y);
        return getPieceFromPositionOptional(position.get());
    }

    public void movePiece(Move move) throws PieceNotFoundException {
        Position from = move.getFrom();
        Position to = move.getTo();

        if(move.getMovementType().equals(MovementType.ATTACK) || move.getMovementType().equals(MovementType.PASSING)) {
            killPiece(move.getPieceAffected());
        }

        Piece piece = getPieceFromPosition(from);
        listPieces.remove(from);
        listPieces.put(to, piece);

        addMoveToHistoric(move, piece);
    }

    private void killPiece(Piece pieceToKill) throws PieceNotFoundException {
        listPieces.values().removeIf(piece -> piece == pieceToKill);
        deadPieces.add(pieceToKill);
    }

    private void handleChecksFromPosition() {

    }

    public void promotePawn(Position position, Piece piece) {
        listPieces.remove(position);
        listPieces.put(position, new Queen(piece.getColor()));
    }

    public void addPiece(Piece piece, Position position) {
        listPieces.put(position, piece);
    }

    public void castle(Color color) {
        if (color.equals(Color.BLACK)) {
            this.blackCastled = true;
        } else {
            this.whiteCastled = true;
        }
    }

    private void addMoveToHistoric(Move move, Piece piece) {
        HistoricMovement historic = HistoricMovement.builder().move(move).piece(piece).build();
        if (piece.getColor().equals(Color.BLACK)) {
            historicBlackMovements.add(historic);
        } else {
            historicWhiteMovements.add(historic);
        }
    }

    public void updateBoardDataPostMovement() {
        handleChecksFromPosition();
    }

    public boolean hasPieceAlreadyMoved(Position position, Color color) {
        Piece piece = getPieceFromPositionOptional(position).get();
        return getHistoricByColor(color).stream().
                anyMatch(historic -> historic.getPiece() == piece); // We want same exact object instance
    }

    private List<HistoricMovement> getHistoricByColor(Color color) {
        if (color.equals(Color.BLACK)) {
            return historicBlackMovements;
        } else {
            return historicWhiteMovements;
        }
    }

    public Piece isPossiblePassingAttack(Position position, Color color) {
        Optional<Position> positionPossibleCapturedPiece = Position.valueOf(position.getX(), position.getY() + (-getMovementDirectionFromColor(color)));
        boolean passingAttack = positionPossibleCapturedPiece.map(position1 -> hasPieceWasLastMovement(position1, Color.getOtherPlayerColor(color))).orElse(false);
        return passingAttack ? getPieceFromPositionOptional(positionPossibleCapturedPiece.get()).get() : null;
    }

    public int getMovementDirectionFromColor(Color color) {
        return color.equals(Color.WHITE) ? BoardData.WHITE_DIRECTION : BoardData.BLACK_DIRECTION;
    }

    private boolean hasPieceWasLastMovement(Position position, Color color) {
        Optional<Piece> piece = getPieceFromPositionOptional(position);
        List<HistoricMovement> historicMovements = getHistoricByColor(color);
        if (!historicMovements.isEmpty() && piece.isPresent()) {
            return historicMovements.get(historicMovements.size() - 1).getPiece() == piece.get()
                    && historicMovements.get(historicMovements.size() - 1).getMove().getMovementType().equals(MovementType.DOUBLE_PAWN);
        } else {
            return false;
        }
    }

    public Map<Position, Piece> getCopyListPieces() {
        return new HashMap<Position,Piece>(listPieces);
    }

    public Map<Position, Piece> doMockupMovement(Move move) {
        Map<Position, Piece> mockup = getCopyListPieces();
        Position from = move.getFrom();
        Position to = move.getTo();

        if(move.getMovementType().equals(MovementType.ATTACK) || move.getMovementType().equals(MovementType.PASSING)) {
            mockup.values().removeIf(piece -> piece == move.getPieceAffected());
        }

        Piece piece = Optional.ofNullable(mockup.get(from)).get();
        mockup.remove(from);
        mockup.put(to, piece);
        return mockup;
    }

    public Optional<Piece> isPlayerCheckedAfterPossibleMovement(Map<Position, Piece> mockupPiecesAfterMovement, Color playersColor) {
        Position playersKingPosition = getPlayersKingPosition(playersColor, mockupPiecesAfterMovement);
        return isPlayerPositionAttackedByEnemy(playersColor, playersKingPosition, mockupPiecesAfterMovement);
    }

    private Optional<Piece> isPlayerPositionAttackedByEnemy(Color playersColor, Position position, Map<Position, Piece> mockupPieces) {
        Map<Position, Piece> otherPlayersPieces = getPiecesByColor(Color.getOtherPlayerColor(playersColor), mockupPieces);
        BoardData newPossibleBoard = new BoardData();
        newPossibleBoard.initPieces(mockupPieces);

        return otherPlayersPieces.entrySet().stream()
                .filter((map) -> {
                    List<Move> movements = map.getValue().getAllValidMovements(map.getKey(), newPossibleBoard);
                    return movements.stream()
                            .anyMatch(move -> move.getTo().equals(position));

                })
                .map(Map.Entry::getValue)
                .findFirst();
    }

    private Map<Position, Piece> getPiecesByColor(Color playersColor) {
        return this.listPieces.entrySet().stream()
                .filter(map -> map.getValue().getColor().equals(playersColor))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }

    private Map<Position, Piece> getPiecesByColor(Color playersColor, Map<Position, Piece> mockupPieces) {
        return mockupPieces.entrySet().stream()
                .filter(map -> map.getValue().getColor().equals(playersColor))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }

    private Position getPlayersKingPosition(Color playersColor, Map<Position, Piece> mockupPieces) {
        return mockupPieces.entrySet().stream()
                .filter(pieces -> pieces.getValue().getColor().equals(playersColor) && pieces.getValue().getPieceType().equals(PieceTypeConfiguration.KING))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public List<Move> getAllBasicMovementsFromColor(Color color) {
        Map<Position, Piece> piecesByColor = getPiecesByColor(color);
        List<Move> possibleMovements = new ArrayList<>();
        piecesByColor.entrySet().stream()
                .map((map) ->  map.getValue().getAllValidMovements(map.getKey(), this))
                .forEach(possibleMovements::addAll);

        return possibleMovements;
    }
}
