package com.guillem.gvilachess.exceptions;

/**
 * Created by guillem on 20/02/2019.
 */
public class NotPlayersTurnException extends Exception {
    public NotPlayersTurnException(String message) {
        super(message);
    }
}
