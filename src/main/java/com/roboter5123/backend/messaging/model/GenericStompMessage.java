package com.roboter5123.backend.messaging.model;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

public class GenericStompMessage extends StompMessage {

    private Object body;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof GenericStompMessage message)) return false;
        return body.equals(message.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(body);
    }
}
