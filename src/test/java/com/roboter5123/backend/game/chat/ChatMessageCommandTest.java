package com.roboter5123.backend.game.chat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageCommandTest {

    @Test
    void testHashCode() {

        ChatMessageCommand expected = new ChatMessageCommand("roboter5123",new ChatMessageAction());
        ChatMessageCommand result = new ChatMessageCommand("roboter5123",new ChatMessageAction());

        assertEquals(expected.hashCode(),result.hashCode());
        assertEquals(expected,result);
        assertEquals(expected,result);
        assertEquals(expected.toString(),result.toString());
    }
}