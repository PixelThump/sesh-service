package com.roboter5123.backend.game.api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinUpdate {

    Map<String, Object> gameState;
    Command command;
}
