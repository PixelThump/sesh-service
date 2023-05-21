package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.game.JoinUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatJoinUpdate implements JoinUpdate {

    private GameState gameState;

    private Command joinCommand;
}
