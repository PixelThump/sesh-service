package com.roboter5123.play.backend.seshservice.api.api;
import com.roboter5123.play.backend.seshservice.messaging.model.message.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.messaging.model.message.StompMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;

public interface StompController {

    /**
     * @param playerName The playerName the client wants to have.
     * @param seshCode The seshCode of the sesh the client wants to join.
     * @param socketId Gotten from message headers. Used to set the playerId. Playerid is used in commands.
     * @return A message. Message contains either the state of the sesh or an error if there was one.
     */
    StompMessage joinSeshAsController(final String playerName, final String seshCode, final @Header("simpSessionId") String socketId);

    /**
     * @param seshCode The seshCode of the sesh the client wants to join as host.
     * @param socketId Gotten from message headers. Used to set the playerId. Playerid is used in commands.
     * @return A message. Message contains either the state of the sesh or an error if there was one.
     */
    StompMessage joinSeshAsHost(final String seshCode, final @Header("simpSessionId") String socketId);

    /**
     * @param message The message the client wants to send to the sesh.
     * @param seshCode The seshCode of the sesh the client wants to message.
     * @param socketId socketId Gotten from message headers. Used to identify a player
     * @return A message. Message contains either the state of the sesh or an error if there was one.
     */
    @MessageMapping("/topic/sesh/{seshCode}")
    StompMessage sendCommandToSesh(CommandStompMessage message, @DestinationVariable String seshCode, @Header("simpSessionId") String socketId);
}
