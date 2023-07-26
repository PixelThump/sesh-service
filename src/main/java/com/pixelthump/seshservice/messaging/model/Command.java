package com.pixelthump.seshservice.messaging.model;
import com.pixelthump.seshservice.messaging.model.action.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {

    private String playerId;
    private Action<?> action;
}
