package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class ChatMessageCommand implements Command {

    private String player;

    private ChatMessageAction action;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof ChatMessageCommand command)) return false;
        return player.equals(command.player) && action.equals(command.action);
    }

    @Override
    public int hashCode() {

        return Objects.hash(player, action);
    }
}
