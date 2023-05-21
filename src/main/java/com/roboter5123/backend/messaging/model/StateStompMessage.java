package com.roboter5123.backend.messaging.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateStompMessage extends StompMessage {

    Map<String, Object> body;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof StateStompMessage that)) return false;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(body);
    }
}
