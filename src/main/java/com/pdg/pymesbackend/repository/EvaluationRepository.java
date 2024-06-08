package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Evaluation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EvaluationRepository extends MongoRepository<Evaluation, String> {

    @Query("{ '_id': { $in: ?0 }, 'completed': ?1 }")
    List<Evaluation> findAllByEvaluationIdAndCompleted(List<String> evaluationId, boolean completed);
}
