package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Message;
import com.roboter5123.backend.service.MessageType;

import java.util.List;
import java.util.Map;

public class ChatStateMessage implements Message<Map<String, List<String>>> {

    private Map<String, List<String>> messageBody;
    private MessageType type;

    public ChatStateMessage(Map<String, List<String>> state) {

        this.messageBody = state;
        this.type = MessageType.STATE;
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
    public void setMessageBody(Map<String, List<String>> body) {

        this.messageBody = body;
    }

    @Override
    public Map<String, List<String>> getMessageBody() {

        return this.messageBody;
    }
}
