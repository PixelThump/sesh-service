package com.roboter5123.backend.messaging.model;
import com.roboter5123.backend.game.api.GameMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpGame {

    private GameMode gameMode;
    private String sessionCode;
}
