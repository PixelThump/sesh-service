package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Action;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageAction implements Action {

    private String type;

    private String body;
}
