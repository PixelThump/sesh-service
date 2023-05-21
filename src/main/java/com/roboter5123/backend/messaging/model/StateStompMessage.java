package com.roboter5123.backend.messaging.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateStompMessage extends StompMessage {

    Map<String, Object> body;
}
