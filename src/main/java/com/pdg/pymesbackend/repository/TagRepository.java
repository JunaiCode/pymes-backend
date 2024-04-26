package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String>{

    Optional<Tag> findByName(String name);
    Optional<Tag> findByNameAndDimensionId(String name, String dimensionId);

    List<Tag> findByDimensionId(String dimensionId);
}
