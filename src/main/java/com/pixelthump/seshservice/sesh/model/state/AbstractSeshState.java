package com.pixelthump.seshservice.sesh.model.state;
import com.pixelthump.seshservice.sesh.api.Player;
import com.pixelthump.seshservice.sesh.model.SeshStage;
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
