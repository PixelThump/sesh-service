package com.roboter5123.play.backend.api.model;
import com.roboter5123.play.backend.sesh.api.SeshType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpSeshDTO {

    private SeshType seshType;
    private String sessionCode;
}
