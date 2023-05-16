package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.game.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatState implements GameState<List<String>> {

     private final List<String> chatter;
     private final List<String> chattLog;

     private Message<String> joinMessage;

    public ChatState() {

        this.chatter = new ArrayList<>();
        this.chattLog = new ArrayList<>();
    }

    @Override
    public Message<String> getJoinMessage() {

        return this.joinMessage;
    }

    @Override
    public Message<Map<String, List<String>>> getStateMessage() {

        Map<String, List<String>> state = new HashMap<>();
        state.put("Chatters", chatter);
        state.put("chatLog", chattLog);
        return new ChatStateMessage(state);
    }

    public void join(String playerName) {

        this.chatter.add(playerName);
        this.chattLog.add(playerName + " joined the Conversation");
        this.joinMessage = new ChatJoinMessage(playerName + " joined the Conversation");
    }
}
