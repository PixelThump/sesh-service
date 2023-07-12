package com.roboter5123.play.backend.seshservice.messaging.model.message;
import com.roboter5123.play.backend.seshservice.sesh.model.AbstractSeshState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StateStompMessage implements StompMessage {

    AbstractSeshState state;
}
