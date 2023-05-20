package com.roboter5123.backend.messaging.model;
import com.roboter5123.backend.game.Command;

public class CommandStompMessage extends StompMessage{

    Command body;

    public Command getBody() {

        return body;
    }

    public void setBody(Command body) {

        this.body = body;
    }
}
