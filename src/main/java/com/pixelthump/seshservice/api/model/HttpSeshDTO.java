package com.pixelthump.seshservice.api.model;
import com.pixelthump.seshservice.sesh.model.SeshType;
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
