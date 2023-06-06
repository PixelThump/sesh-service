package com.roboter5123.play.backend.game.implementation.chat;
import com.roboter5123.play.messaging.model.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageAction implements Action {

    private String message;
}
