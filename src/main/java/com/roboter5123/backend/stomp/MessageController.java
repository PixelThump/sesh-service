package com.roboter5123.backend.stomp;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    private final List<String> messages = new ArrayList<>();

    @SubscribeMapping("/topic/demo")
    public List<String> subscribe(){

        return messages;
    }

    @MessageMapping("/topic/demo")
    public String message(String message){

        messages.add(message);
        return message;
    }
}
