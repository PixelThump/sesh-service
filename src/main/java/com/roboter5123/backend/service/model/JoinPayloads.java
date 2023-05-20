package com.roboter5123.backend.service.model;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;

public class JoinPayloads {

private GameState reply;
private Command broadcast;

    public GameState getReply() {

        return reply;
    }

    public void setReply(GameState reply) {

        this.reply = reply;
    }

    public Command getBroadcast() {

        return broadcast;
    }

    public void setBroadcast(Command broadcast) {

        this.broadcast = broadcast;
    }
}
