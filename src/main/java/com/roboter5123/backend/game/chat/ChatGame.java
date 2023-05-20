package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameState;

import java.util.HashMap;
import java.util.Map;

public class ChatGame implements Game {

    ChatState chatState;

    public ChatGame() {

        this.chatState = new ChatState();
    }

    @Override
    public Map<String, Object> joinGame(String playerName) {

        this.chatState.join(playerName);
        Command joinCommand = new ChatMessageCommand("server", new ChatMessageAction("MESSAGE", playerName + " has joined the conversation!"));

        Map<String, Object> joinUpdate = new HashMap<>();
        joinUpdate.put("state", this.chatState);
        joinUpdate.put("joinCommand", joinCommand);

        return joinUpdate;
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
