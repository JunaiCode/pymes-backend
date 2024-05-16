package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String>{
}
