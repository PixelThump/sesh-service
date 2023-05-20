package com.roboter5123.backend.game;
public interface JoinUpdate {

    GameState getGameState();
    void setGameState(GameState state);
    Command getJoincommand();
    void setJoinCommand(Command command);
}
