package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Dimension;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DimensionRepository extends MongoRepository<Dimension, String> {


    Optional<Dimension> findByName(String name);
}
