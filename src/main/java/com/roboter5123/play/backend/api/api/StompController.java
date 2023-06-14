package com.roboter5123.play.backend.api.api;
import com.roboter5123.play.backend.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.messaging.model.StompMessage;

public interface StompController {

    StompMessage joinSession(final String playerName, final String sessionCode);

    StompMessage sendCommandToGame(final CommandStompMessage message, final String sessionCode);
}
