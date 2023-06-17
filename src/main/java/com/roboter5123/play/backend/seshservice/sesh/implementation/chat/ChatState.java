package com.roboter5123.play.backend.seshservice.sesh.implementation.chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatState {

    private List<String> chatters = new ArrayList<>();
    private List<String> chatLog = new ArrayList<>();

    public String join(String playerName) {

        this.chatters.add(playerName);
        String message = playerName + " joined the conversation";
        this.chatLog.add(message);
        return message;
    }

    public Map<String, Object> getState() {

        Map<String, Object> state = new HashMap<>();
        state.put("chatters", this.chatters);
        state.put("chatLog", this.chatLog);
        return state;
    }

    public String addMessage(String playerName, String message) {

        String addedMessage = playerName + ": " + message;
        this.chatLog.add(addedMessage);
        return addedMessage;
    }
}
