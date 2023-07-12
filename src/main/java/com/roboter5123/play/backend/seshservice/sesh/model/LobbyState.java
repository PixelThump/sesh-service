package com.roboter5123.play.backend.seshservice.sesh.model;
import lombok.Data;

import java.util.Objects;

@Data
public class LobbyState extends AbstractSeshState {

    private Integer maxPlayers;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof LobbyState that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(maxPlayers, that.maxPlayers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), maxPlayers);
    }
}
