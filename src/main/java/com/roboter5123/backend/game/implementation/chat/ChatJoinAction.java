package com.roboter5123.backend.game.implementation.chat;
import com.roboter5123.backend.game.api.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatJoinAction implements Action {

    private String joiningPlayer;
}