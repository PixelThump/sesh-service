package com.roboter5123.play.backend.api.model;
import com.roboter5123.play.backend.game.api.GameMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpGameDTO {

    private GameMode gameMode;
    private String sessionCode;
}
