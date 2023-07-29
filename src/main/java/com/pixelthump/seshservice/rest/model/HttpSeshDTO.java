package com.pixelthump.seshservice.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpSeshDTO {

    private String seshType;
    private String seshCode;
}
