package com.roboter5123.backend.messaging.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericStompMessage extends StompMessage {

    Object body;
}
