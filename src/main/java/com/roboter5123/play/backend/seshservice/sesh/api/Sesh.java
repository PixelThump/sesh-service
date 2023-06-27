package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;

import java.time.LocalDateTime;
import java.util.Map;

public interface Sesh {

    Map<String, Object> joinSesh(String playerName) throws PlayerAlreadyJoinedException, SeshIsFullException;

    SeshType getSeshType();

    String getSeshCode();

    void broadcast(Object payload);

    void addCommand(Command command) throws UnsupportedOperationException;

    LocalDateTime getLastInteractionTime();
}
