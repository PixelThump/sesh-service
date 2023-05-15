package com.roboter5123.backend.service.model.action;
import com.roboter5123.backend.service.model.enums.Type;

public interface Action {

    Type getType();
    void setType(Type type);
}
