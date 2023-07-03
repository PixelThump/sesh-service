package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;

public interface SeshLobby {

    boolean processLobbyCommand(Command command);
}
