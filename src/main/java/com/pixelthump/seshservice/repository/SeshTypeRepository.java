package com.pixelthump.seshservice.repository;
import com.pixelthump.seshservice.repository.model.SeshType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SeshTypeRepository extends CrudRepository<SeshType, String> {

    Optional<SeshType> findByName(String name);

    @Override
    List<SeshType> findAll();
}
