package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;

import java.time.LocalDateTime;
import java.util.Map;

public interface Sesh {

    Map<String, Object> joinSesh(String playerName);

    SeshType getSeshType();

    void setSeshType(SeshType seshType);

    void setSeshCode(String sessionCode);

    String getSeshCode();

    void broadcast(Object joinCommand);

    void addCommand(Command command) throws UnsupportedOperationException;

    LocalDateTime getLastInteractionTime();
}
