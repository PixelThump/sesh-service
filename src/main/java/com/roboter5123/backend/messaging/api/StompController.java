package com.roboter5123.backend.messaging.api;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.service.model.StompMessage;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;

public interface StompController {

    String createSession(GameMode gameMode) throws TooManySessionsException;
    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
}
