package com.roboter5123.backend.game;
public interface JoinUpdate {

    GameState getGameState();
    void setGameState(GameState state);
    Command getJoinCommand();
    void setJoinCommand(Command command);
}
