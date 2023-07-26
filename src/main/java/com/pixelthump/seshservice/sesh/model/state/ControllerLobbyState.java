package com.pixelthump.seshservice.sesh.model.state;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ControllerLobbyState extends AbstractSeshState{

    private boolean hasVip;

}
