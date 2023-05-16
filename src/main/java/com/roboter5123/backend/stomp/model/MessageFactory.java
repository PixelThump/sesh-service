package com.roboter5123.backend.stomp.model;
public class MessageFactory {

    private MessageFactory() {

    }

    public static StompMessage getMessage(String type, Object body){

        if (type.equals("error")){

            StompMessage message = new StompMessage();
            message.setMessageType(StompMessageType.ERROR);
            message.setMessageBody(body);
            return message;
        }
        return new StompMessage();
    }
}
