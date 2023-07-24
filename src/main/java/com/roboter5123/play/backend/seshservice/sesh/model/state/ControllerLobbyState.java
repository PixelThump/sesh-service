package com.roboter5123.play.backend.seshservice.sesh.model.state;
import lombok.Data;

import java.util.Objects;

@Data
public class ControllerLobbyState extends AbstractSeshState{

    private boolean hasVip;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof ControllerLobbyState that)) return false;
        if (!super.equals(o)) return false;
        return hasVip == that.hasVip;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), hasVip);
    }
}
