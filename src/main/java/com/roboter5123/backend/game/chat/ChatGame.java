package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameState;

public class ChatGame implements Game {

    ChatState chatState;

    public ChatGame() {

        this.chatState = new ChatState();
    }

    @Override
    public GameState joinGame(String playerName) {

        this.chatState.join(playerName);
        return chatState;
    }

    @Override
    public void addCommand(Command command) {
//TODO:IMPLEMENT
    }

    @Override
    public GameState processCommands() {

        return null;
    }
}
