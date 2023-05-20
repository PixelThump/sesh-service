package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.game.JoinUpdate;

public class ChatJoinUpdate implements JoinUpdate {

    private GameState gameState;

    private Command command;

    @Override
    public GameState getGameState() {

        return this.gameState;
    }

    @Override
    public void setGameState(GameState state) {

        this.gameState = state;
    }

    @Override
    public Command getJoincommand() {

        return this.command;
    }

    @Override
    public void setJoinCommand(Command command) {

        this.command = command;
    }
}
