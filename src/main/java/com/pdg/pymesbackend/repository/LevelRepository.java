package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Level;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LevelRepository extends MongoRepository<Level, String> {
}
