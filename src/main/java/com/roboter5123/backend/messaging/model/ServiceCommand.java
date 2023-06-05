package com.roboter5123.backend.messaging.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCommand {

    private String player;
    private Object action;
}
