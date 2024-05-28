package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Version;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VersionRepository extends MongoRepository<Version, String>{

    Optional<Version> findByVersionId(String versionId);

    Optional<Version> findByName(String name);

    List<Version> findVersionByVersionIdIn(List<String> versionIds);

    @Query(value = "{ 'dimensions._id' : ?0 }")
    Optional<Version> findVersionByDimensionId(String dimensionId);

    @Query(value = "{ 'active' : true }")
    Version findActiveVersion();
}
