package com.roboter5123.play.backend.game.implementation.chat;
import com.roboter5123.play.backend.messaging.model.Command;
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
    private Command lastCommand;

    public void join(String playerName) {

        this.chatters.add(playerName);
        this.chatLog.add(playerName + " joined the Conversation");
        this.lastCommand = new Command("server", new ChatJoinAction(playerName));
    }

    public Map<String, Object> getState() {

        Map<String, Object> state = new HashMap<>();
        state.put("chatters", this.chatters);
        state.put("chatLog", this.chatLog);
        return state;
    }
}
