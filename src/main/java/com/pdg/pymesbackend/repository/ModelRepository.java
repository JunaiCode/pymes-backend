package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModelRepository extends MongoRepository<Model, String> {

}
