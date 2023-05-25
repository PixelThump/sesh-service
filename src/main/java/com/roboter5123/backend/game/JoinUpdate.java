package com.roboter5123.backend.game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinUpdate {

    GameState gameState;

    Command command;
}
