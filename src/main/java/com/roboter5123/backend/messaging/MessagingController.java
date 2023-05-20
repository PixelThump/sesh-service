package com.roboter5123.backend.messaging;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.messaging.model.StompMessage;

public interface MessagingController {

    String createSession(GameMode gameMode);
    StompMessage joinSession(String playerName, String sessionCode);
    void broadcast(String sessionCode, Object payload);
}
