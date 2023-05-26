package com.roboter5123.backend.messaging.api;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;

public interface MessagingController {

    String createSession(GameMode gameMode) throws TooManySessionsException;
    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
    void broadcast(String sessionCode, Object payload);
}
