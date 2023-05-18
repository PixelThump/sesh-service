package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatState implements GameState {

     private final List<String> chatter;
     private final List<String> chattLog;

     private Command lastJoinCommand;

    public ChatState() {

        this.chatter = new ArrayList<>();
        this.chattLog = new ArrayList<>();
    }

    public void join(String playerName) {

        this.chatter.add(playerName);
        this.chattLog.add(playerName + " joined the Conversation");
        this.lastJoinCommand = new ChatMessageCommand("server", new ChatMessageAction("MESSAGE", playerName + " has joined the conversation!"));
    }

    @Override
    public Command getLastCommand() {

        return this.lastJoinCommand;
    }

    @Override
    public Map<String, Object> getState() {

        Map<String,Object> state = new HashMap<>();
        state.put("chatlog", this.chattLog);
        state.put("chatters", this.chatter);
        return state;
    }
}
