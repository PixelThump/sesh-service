package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.game.JoinUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatJoinUpdate implements JoinUpdate {

    private GameState gameState;

    private Command joinCommand;
}
