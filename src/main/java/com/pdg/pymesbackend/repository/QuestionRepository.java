package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String>{

    List<Question> findByTagId(String tagId);

    @Query("{ 'questionId': { '$in': ?0 }, 'companyTypeId': ?1 }")
    List<Question> findQuestionsByCompanyType(List<String> id, String companyTypeId);
}
