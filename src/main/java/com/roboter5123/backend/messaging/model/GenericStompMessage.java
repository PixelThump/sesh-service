package com.roboter5123.backend.messaging.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericStompMessage extends StompMessage {

    Object body;
}
