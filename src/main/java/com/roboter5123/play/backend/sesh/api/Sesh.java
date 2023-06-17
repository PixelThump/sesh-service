package com.roboter5123.play.backend.sesh.api;
import com.roboter5123.play.backend.messaging.model.Command;

import java.util.Map;

public interface Sesh {

    Map<String, Object> joinSesh(String playerName);

    SeshType getSeshType();

    void setSeshType(SeshType seshType);

    void setSeshCode(String sessionCode);

    String getSeshCode();

    void broadcast(Object joinCommand);

    void addCommand(Command command) throws UnsupportedOperationException;
}
