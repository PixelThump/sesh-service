package com.roboter5123.play.backend.service.exception;
public class TooManySessionsException extends RuntimeException {

    public TooManySessionsException(String errorMessage) {

        super(errorMessage);
    }
}
