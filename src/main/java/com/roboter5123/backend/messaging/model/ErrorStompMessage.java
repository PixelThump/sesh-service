package com.roboter5123.backend.messaging.model;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorStompMessage extends StompMessage{

    private String body;
}
