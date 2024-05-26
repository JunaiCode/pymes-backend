package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.EvaluationResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EvaluationResultRepository extends MongoRepository<EvaluationResult, String>{

    @Query(value = "{ '_id' : { $in: ?0 } }", delete = true)
    void deleteAllByEvaluationResultIdIn(List<String> evaluationResultId);

}
