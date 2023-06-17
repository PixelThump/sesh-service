package com.roboter5123.play.backend.api.api;
import com.roboter5123.play.backend.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.messaging.model.StompMessage;

public interface StompController {

    StompMessage joinSesh(final String playerName, final String seshCode);

    StompMessage sendCommandToSesh(final CommandStompMessage message, final String seshCode);
}
