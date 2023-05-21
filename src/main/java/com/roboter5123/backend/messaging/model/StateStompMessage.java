package com.roboter5123.backend.messaging.model;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class StateStompMessage extends StompMessage {

    Map<String, Object> body;
}
