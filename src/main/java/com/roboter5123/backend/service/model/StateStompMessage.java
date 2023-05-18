package com.roboter5123.backend.service.model;
import java.util.Map;

public class StateStompMessage extends StompMessage {

    Map<String, Object> body;

    public Map<String, Object> getBody() {

        return body;
    }

    public void setBody(Map<String, Object> body) {

        this.body = body;
    }
}
