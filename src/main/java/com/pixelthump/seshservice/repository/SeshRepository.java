package com.pixelthump.seshservice.repository;
import com.pixelthump.seshservice.repository.model.Sesh;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SeshRepository extends CrudRepository<Sesh,String> {

    Optional<Sesh> findBySeshCode(String seshCode);

    @Override
    List<Sesh> findAll();
}
