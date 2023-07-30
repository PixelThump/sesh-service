package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.repository.model.SeshType;

import java.util.List;

public interface SeshTypeService {

    List<SeshType> getSeshtypes();

    SeshType getSeshTypeByName(String seshTypeName);

}
