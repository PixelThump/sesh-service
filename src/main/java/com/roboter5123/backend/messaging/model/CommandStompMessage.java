package com.roboter5123.backend.messaging.model;
import com.roboter5123.backend.game.Command;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandStompMessage extends StompMessage{

    Command body;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof CommandStompMessage that)) return false;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(body);
    }
}
