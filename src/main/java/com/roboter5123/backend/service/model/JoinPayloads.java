package com.roboter5123.backend.service.model;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.game.api.GameState;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinPayloads {

private GameState reply;
private Command broadcast;

}
