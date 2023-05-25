package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageCommand extends Command {

    private ChatMessageAction action;

    public ChatMessageCommand(String player, ChatMessageAction action) {

        super(player);
        this.action = action;
    }
}
