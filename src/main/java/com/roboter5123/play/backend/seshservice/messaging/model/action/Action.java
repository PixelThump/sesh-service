package com.roboter5123.play.backend.seshservice.messaging.model.action;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.action.NextQuestionAction;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
//formatter:off
@JsonSubTypes({
        @JsonSubTypes.Type(value = BasicAction.class, name = "basic"),
        @JsonSubTypes.Type(value = MakeVIPAction.class, name = "makeVip"),
        @JsonSubTypes.Type(value = StartSeshAction.class, name = "startSesh"),
        @JsonSubTypes.Type(value = NextQuestionAction.class, name = "nextQuestion")
})
//formatter:on
public interface Action {

    String getTargetId();
}