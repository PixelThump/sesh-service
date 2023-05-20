package com.roboter5123.backend.messaging.model;
public class GenericStompMessage extends StompMessage {

    Object body;

    public Object getBody() {

        return body;
    }

    public void setBody(Object body) {

        this.body = body;
    }
}
