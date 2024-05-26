package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.EvaluationResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EvaluationResultRepository extends MongoRepository<EvaluationResult, String>{

    void deleteAllByEvaluationResultIdIn(List<String> evaluationResultId);

}
