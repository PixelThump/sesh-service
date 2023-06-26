package com.roboter5123.play.backend.seshservice.sesh.implementation.chat;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class ChatState {

    private List<String> chatters = new ArrayList<>();
    private List<String> chatLog = new ArrayList<>();

    public String join(String playerName)throws PlayerAlreadyJoinedException {

        if (chatters.contains(playerName)){
            String errormessage = "Player with name " + playerName + " is already in the Sesh";
            log.error(errormessage);
            throw new PlayerAlreadyJoinedException(errormessage);
        }
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
