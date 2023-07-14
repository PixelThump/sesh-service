package com.roboter5123.play.backend.seshservice.sesh.model.state;
import lombok.Data;

import java.util.Objects;

@Data
public class HostLobbyState extends AbstractSeshState{

    private int maxPlayers;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof HostLobbyState that)) return false;
        if (!super.equals(o)) return false;
        return maxPlayers == that.maxPlayers;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), maxPlayers);
    }
}
