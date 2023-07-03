package com.roboter5123.play.backend.seshservice.messaging.model.action;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = BasicAction.class, name = "basic"), @JsonSubTypes.Type(value = MakeVIPAction.class, name = "makeVip"), @JsonSubTypes.Type(value = StartSeshAction.class, name = "startSesh"),})
public interface Action {

}