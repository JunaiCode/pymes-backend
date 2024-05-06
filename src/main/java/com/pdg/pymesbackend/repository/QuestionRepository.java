package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {

    List<Question> findByTagId(String tagId);
}
