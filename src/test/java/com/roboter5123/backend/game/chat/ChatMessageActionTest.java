package com.roboter5123.backend.game.chat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageActionTest {



    @Test
    void setType() {

        ChatMessageAction expected = new ChatMessageAction();
        expected.setType("FUCK YOU JACOCO");
        expected.setBody("DOUBLE FUCK YOU JACOCO");
        ChatMessageAction result = new ChatMessageAction();
        result.setType("FUCK YOU JACOCO");
        result.setBody("DOUBLE FUCK YOU JACOCO");

        assertEquals(expected.hashCode(),result.hashCode());
        assertEquals(expected,result);
        assertEquals(expected,result);
    }
}