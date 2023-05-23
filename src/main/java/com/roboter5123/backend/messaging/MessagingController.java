package com.roboter5123.backend.messaging;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.exception.TooManySessionsException;

public interface MessagingController {

    String createSession(GameMode gameMode) throws TooManySessionsException;
    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
    void broadcast(String sessionCode, Object payload);
}
