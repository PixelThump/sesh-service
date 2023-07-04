package com.roboter5123.play.backend.seshservice.api.api;
import com.roboter5123.play.backend.seshservice.messaging.model.message.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.messaging.model.message.StompMessage;
import org.springframework.messaging.handler.annotation.Header;

public interface StompController {

    StompMessage joinSeshAsController(final String playerName, final String seshCode, final @Header("simpSessionId") String socketId);

    StompMessage joinSeshAsHost(final String seshCode, final @Header("simpSessionId") String socketId);

    StompMessage sendCommandToSesh(final CommandStompMessage message, final String seshCode);
}
