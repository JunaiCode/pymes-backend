package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ModelRepository extends MongoRepository<Model, String> {

    @Query("{ 'active' : true }")
    Model findActiveModel();

}
