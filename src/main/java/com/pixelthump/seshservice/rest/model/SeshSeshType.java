package com.pixelthump.seshservice.rest.model;
import lombok.Data;

@Data
public class SeshSeshType {

    String name;

    public SeshSeshType() {

    }

    public SeshSeshType(String name) {

        this.name = name;
    }
}
