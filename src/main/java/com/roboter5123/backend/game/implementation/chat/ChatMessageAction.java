package com.roboter5123.backend.game.implementation.chat;
import com.roboter5123.backend.game.api.Action;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageAction implements Action {

    private String message;
}
