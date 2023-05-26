package com.roboter5123.backend.game.implementation.chat;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.game.api.GameState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatState implements GameState {

    private List<String> chatter = new ArrayList<>();
    private List<String> chatLog = new ArrayList<>();
    private Command lastCommand;

    public void join(String playerName) {

        this.chatter.add(playerName);
        this.chatLog.add(playerName + " joined the Conversation");
        this.lastCommand = new Command("server", new ChatJoinAction(playerName));
    }
}
