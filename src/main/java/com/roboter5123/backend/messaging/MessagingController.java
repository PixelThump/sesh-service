package com.roboter5123.backend.messaging;
import com.roboter5123.backend.messaging.model.StompMessage;

public interface MessagingController {

    String createSession();
    StompMessage joinSession(String playerName, String sessionCode);
    void broadcast(String sessionCode, Object payload);
}
