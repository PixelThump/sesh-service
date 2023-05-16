package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Message;
import com.roboter5123.backend.service.MessageType;

public class ChatJoinMessage implements Message<String> {

    private String messageBody;
    private MessageType type;

    public ChatJoinMessage(String s) {

        this.setMessageBody(s);
        this.setType(MessageType.JOIN);
    }

    @Override
    public MessageType getType() {

        return this.type;
    }

    @Override
    public void setType(MessageType newType) {

        this.type = newType;
    }

    @Override
    public void setMessageBody(String body) {

        this.messageBody = body;
    }

    @Override
    public String getMessageBody() {

        return this.messageBody;
    }
}
