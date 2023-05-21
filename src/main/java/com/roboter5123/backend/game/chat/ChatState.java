package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ChatState implements GameState {

    private List<String> chatter;
    private List<String> chatLog;
    private Command lastCommand;

    public ChatState() {

        this.chatter = new ArrayList<>();
        this.chatLog = new ArrayList<>();
    }

    public void join(String playerName) {

        this.chatter.add(playerName);
        this.chatLog.add(playerName + " joined the Conversation");
        this.lastCommand = new ChatMessageCommand("server", new ChatMessageAction("MESSAGE", playerName + " has joined the conversation!"));
    }

    @Override
    public Command getLastCommand() {

        return this.lastCommand;
    }

    @Override
    public Map<String, Object> getState() {

        Map<String, Object> state = new HashMap<>();
        state.put("chatlog", this.chatLog);
        state.put("chatters", this.chatter);
        return state;
    }
}
