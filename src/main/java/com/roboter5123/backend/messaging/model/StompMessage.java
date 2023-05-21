package com.roboter5123.backend.messaging.model;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class StompMessage {

    private StompMessageType messageType;
}
