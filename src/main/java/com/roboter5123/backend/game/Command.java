package com.roboter5123.backend.game;
public interface Command {

    String getPlayer();
    void setPlayer(String playerName);
    Action getAction();
    void setAction(Action action);

}
