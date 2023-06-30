package com.roboter5123.play.backend.seshservice.api.api;
import com.roboter5123.play.backend.seshservice.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.messaging.model.StompMessage;

public interface StompController {

    StompMessage joinSeshAsController(final String playerName, final String seshCode);

    StompMessage joinSeshAsHost(final String seshCode);

    StompMessage sendCommandToSesh(final CommandStompMessage message, final String seshCode);
}
