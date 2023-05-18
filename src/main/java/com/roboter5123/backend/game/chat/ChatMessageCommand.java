package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Action;
import com.roboter5123.backend.game.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatMessageCommand implements Command {

    private String player;

    private ChatMessageAction action;



    @Override
    public String getPlayer() {

        return player;
    }

    @Override
    public void setPlayer(String player) {

        this.player = player;
    }

    @Override
    public ChatMessageAction getAction() {

        return action;
    }

    public void setAction(Action action) {


        this.action = (ChatMessageAction) action;
    }
}
