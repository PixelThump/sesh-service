package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageCommand implements Command {

    private String player;
    private ChatMessageAction action;
}
