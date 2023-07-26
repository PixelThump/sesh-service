package com.pixelthump.seshservice.messaging.model.message;
import com.pixelthump.seshservice.messaging.model.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CommandStompMessage implements StompMessage {

    Command command;
}
