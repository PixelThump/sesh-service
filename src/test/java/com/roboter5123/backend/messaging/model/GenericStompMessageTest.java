package com.roboter5123.backend.messaging.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericStompMessageTest {

    @Test
    void getBody() {

        GenericStompMessage expected = new GenericStompMessage();
        expected.setBody("");
        expected.setMessageType(StompMessageType.COMMAND);
        GenericStompMessage result = new GenericStompMessage();
        result.setBody("");
        result.setMessageType(StompMessageType.COMMAND);

        assertEquals(expected.hashCode(),result.hashCode());
        assertEquals(expected,result);
        assertEquals(expected,result);
        assertEquals(expected.toString(),result.toString());
    }
}