package com.pixelthump.seshservice.repository;
import com.pixelthump.seshservice.repository.model.SeshType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeshTypeRepository extends CrudRepository<SeshType, String> {

    @Override
    List<SeshType> findAll();
}
