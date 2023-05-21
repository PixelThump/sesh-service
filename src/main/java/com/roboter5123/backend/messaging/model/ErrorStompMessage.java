package com.roboter5123.backend.messaging.model;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStompMessage extends StompMessage{

    private String body;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof ErrorStompMessage that)) return false;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(body);
    }
}
