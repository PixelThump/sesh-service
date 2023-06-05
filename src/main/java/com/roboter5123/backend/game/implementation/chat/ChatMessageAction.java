package com.roboter5123.backend.game.implementation.chat;
import com.roboter5123.backend.game.api.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageAction implements Action {

    private String message;
}
