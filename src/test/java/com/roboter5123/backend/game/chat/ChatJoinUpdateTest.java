package com.roboter5123.backend.game.chat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatJoinUpdateTest {

    @Test
    void testToString() {

        ChatJoinUpdate expected = new ChatJoinUpdate();


        ChatJoinUpdate result = new ChatJoinUpdate();

        assertEquals(expected.hashCode(),result.hashCode());
        assertEquals(expected,result);
        assertEquals(expected,result);
        assertEquals(expected.toString(),result.toString());
    }
}