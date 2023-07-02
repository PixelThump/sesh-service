package com.roboter5123.play.backend.seshservice.sesh.model;
import lombok.Data;

import java.util.UUID;

@Data
public class PlayerId {

    String id;

    public PlayerId() {

        this.id = UUID.randomUUID().toString();
    }

    public String getId() {

        return id;
    }

    @Override
    public String toString() {

        return id;
    }
}
