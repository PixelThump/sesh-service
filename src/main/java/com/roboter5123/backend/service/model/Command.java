package com.roboter5123.backend.service.model;
import com.roboter5123.backend.service.model.action.Action;

public interface Command {

    String getPlayer();
    void setPlayer(String playerName);
    Action getAction();
    void setAction(Action action);

}
