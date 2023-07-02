package com.roboter5123.play.backend.seshservice.messaging.model.message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GenericStompMessage implements StompMessage {

    private Object object;
}
