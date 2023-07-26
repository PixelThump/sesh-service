package com.pixelthump.seshservice.messaging.model.message;
import com.pixelthump.seshservice.sesh.model.state.AbstractSeshState;
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
