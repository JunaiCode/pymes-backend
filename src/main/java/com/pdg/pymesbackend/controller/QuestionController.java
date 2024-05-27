package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.QuestionAPI;
import com.pdg.pymesbackend.dto.OptionDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.model.Question;
import com.pdg.pymesbackend.service.modules.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class QuestionController implements QuestionAPI {

    private QuestionService questionService;

    @Override
    public Question createQuestion(QuestionDTO question) {
        return questionService.createQuestion(question);
    }


    @Override
    public Question addOption(OptionDTO option, String questionId) {
        return questionService.addOption(questionId, option);
    }

    @Override
    public void deleteQuestion(String id) {
        questionService.deleteQuestion(id);
    }

    @Override
    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @Override
    public Question getQuestion(String id) {
        return questionService.getQuestion(id);
    }

    @Override
    public List<Question> getQuestionsByTag(String tag) {
        return questionService.getQuestionsByTag(tag);
    }

    @Override
    public List<Question> getQuestionsByLevel(String level) {
        return questionService.getQuestionsByLevel(level);
    }
}
