package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Dimension;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DimensionRepository extends MongoRepository<Dimension, String> {
}
