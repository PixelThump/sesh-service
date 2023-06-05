package com.roboter5123.backend.service.model;
import com.roboter5123.backend.game.api.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinPayloads {

    private Map<String, Object> reply;
    private Command broadcast;
}
