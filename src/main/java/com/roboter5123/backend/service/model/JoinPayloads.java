package com.roboter5123.backend.service.model;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinPayloads {

private GameState reply;
private Command broadcast;

}
