package com.roboter5123.play.backend.seshservice.messaging.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MakeVIPAction implements Action{

    private String playerName;
    private boolean makeVip;
}
