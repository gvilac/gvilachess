package com.guillem.gvilachess.model.domain.chess.enums;

/**
 * Created by guillem on 13/02/2019.
 */
public enum Color {
    BLACK("B"), WHITE("W");

    private String code;

    Color(String code) {
        this.code = code;
    }

    public static Color getOtherPlayerColor(Color currentPlayerSide) {
        return BLACK.equals(currentPlayerSide) ? WHITE : BLACK;
    }

    public String getCode() {
        return code;
    }

}
