package com.roboter5123.play.backend.seshservice.messaging.model.action;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MakeVIPAction implements Action {

    private String playerId;
    private boolean makeVip;
}
