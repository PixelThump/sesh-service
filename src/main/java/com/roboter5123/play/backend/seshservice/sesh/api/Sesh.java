package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;

import java.time.LocalDateTime;
import java.util.Map;

public interface Sesh {

    Map<String, Object> joinSeshAsHost() throws PlayerAlreadyJoinedException;

    Map<String, Object> joinSeshAsController(String playerName) throws SeshIsFullException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException;

    SeshType getSeshType();

    String getSeshCode();
    void setSeshCode(String seshCode);

    void broadcastToAll(Object payload);

    void broadcastToHost(Object payload);

    void broadcastToControllers(Object payload);

    void addCommand(Command command) throws PlayerNotInSeshException;

    LocalDateTime getLastInteractionTime();

    SeshStage getCurrentStage();

    void startSesh();
}
