package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Action;
import com.roboter5123.backend.game.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatMessageCommand implements Command {

    private String player;

    private ChatMessageAction action;


    public String getPlayer() {

        return player;
    }


    public void setPlayer(String playerName) {

        this.player = playerName;
    }


    public ChatMessageAction getAction() {

        return action;
    }

    public void setAction(Action action) {


        this.action = (ChatMessageAction) action;
    }
}
