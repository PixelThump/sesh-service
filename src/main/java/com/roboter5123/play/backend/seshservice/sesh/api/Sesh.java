package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;

import java.time.LocalDateTime;
import java.util.Map;

public interface Sesh {

    Map<String, Object> joinSeshAsHost() throws PlayerAlreadyJoinedException;

    Map<String, Object> joinSeshAsController(String playerName) throws SeshIsFullException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException;

    SeshType getSeshType();

    String getSeshCode();

    void broadcastToAll(Object payload);
    void broadcastToHost(Object payload);
    void broatcastToControllers(Object payload);

    void addCommand(Command command) throws PlayerNotInSeshException;

    LocalDateTime getLastInteractionTime();
}
