package com.roboter5123.backend.messaging.model;
import com.roboter5123.backend.game.api.GameState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StateStompMessage implements StompMessage {

    GameState state;
}
