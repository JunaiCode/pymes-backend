package com.pdg.pymesbackend.service;

import com.pdg.pymesbackend.dto.OptionDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.model.Question;

import java.util.List;

public interface QuestionService {
    Question createQuestion(QuestionDTO questionDTO);
    Question addRecommendation(String questionId, RecommendationDTO recommendation);
    Question addOption(String questionId, OptionDTO option);
    void deleteQuestion(String id);
    List<Question> getQuestions();
    Question getQuestion(String id);
    List<Question> getQuestionsByTag(String tag);

}
