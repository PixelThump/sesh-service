package com.roboter5123.play.backend.seshservice.sesh.model;
import com.roboter5123.play.backend.seshservice.sesh.api.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractSeshState {

    private List<Player> players;
    private String seshCode;
    private SeshStage currentStage;
}
