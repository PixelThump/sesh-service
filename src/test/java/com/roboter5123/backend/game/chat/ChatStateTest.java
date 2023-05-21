package com.roboter5123.backend.game.chat;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatStateTest {

    @Test
    public void testEquals() {

        assertEquals(new ChatState(), new ChatState());
    }

    @Test
    public void testHashCode() {

        assertEquals(new ChatState().hashCode(), new ChatState().hashCode());
    }
}