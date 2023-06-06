package com.roboter5123.play.backend.game.implementation.chat;
import com.roboter5123.play.backend.messaging.model.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatJoinAction implements Action {

    private String joiningPlayer;
}
