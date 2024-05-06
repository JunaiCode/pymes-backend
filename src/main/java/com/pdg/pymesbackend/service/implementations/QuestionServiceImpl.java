package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.OptionDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.OptionMapper;
import com.pdg.pymesbackend.mapper.QuestionMapper;
import com.pdg.pymesbackend.mapper.RecommendationMapper;
import com.pdg.pymesbackend.model.Option;
import com.pdg.pymesbackend.model.Question;
import com.pdg.pymesbackend.model.Recommendation;
import com.pdg.pymesbackend.repository.QuestionRepository;
import com.pdg.pymesbackend.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    private QuestionMapper questionMapper;
    private OptionMapper optionMapper;
    private RecommendationMapper recommendationMapper;


    @Override
    public Question createQuestion(QuestionDTO questionDTO) {

        //validar tag
        //validar dimension
        //validar version
        Question question = questionMapper.fromDTO(questionDTO);
        question.setQuestionId(UUID.randomUUID().toString());
        question.setRecommendations(new ArrayList<>());
        return questionRepository.save(question);
    }

    @Override
    public Question addRecommendation(String questionId, RecommendationDTO recommendation) {

        Question question = findById(questionId);
        Recommendation newRecommendation = recommendationMapper.fromDTO(recommendation);
        newRecommendation.setRecommendationId(UUID.randomUUID().toString());
        question.getRecommendations().add(newRecommendation.getRecommendationId());
        //se debe guardar recomendación en su respectiva tabla (?
        return questionRepository.save(question);
    }

    @Override
    public Question addOption(String questionId, OptionDTO option) {
        Question question = findById(questionId);
        Option newOption = optionMapper.fromDTO(option);
        question.getOptions().add(newOption);
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(String id) {
        Question question = findById(id);
        questionRepository.delete(question);
    }

    private Question findById(String id) {
        return questionRepository.findById(id).orElseThrow(() -> new PymeException(PymeExceptionType.QUESTION_NOT_FOUND));
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestion(String id) {
        return findById(id);
    }

    @Override
    public List<Question> getQuestionsByTag(String tag) {
        return questionRepository.findByTagId(tag);
    }
}
