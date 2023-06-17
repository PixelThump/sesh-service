package com.roboter5123.play.backend.seshservice.api.model;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpSeshDTO {

    private SeshType seshType;
    private String seshCode;
}
