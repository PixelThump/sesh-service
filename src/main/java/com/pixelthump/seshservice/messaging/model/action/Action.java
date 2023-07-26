package com.pixelthump.seshservice.messaging.model.action;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Action<T> {

    private String type;
    private T body;
}