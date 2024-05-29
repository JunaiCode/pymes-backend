package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.OptionMapper;
import com.pdg.pymesbackend.mapper.QuestionMapper;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.LevelRepository;
import com.pdg.pymesbackend.repository.QuestionRepository;
import com.pdg.pymesbackend.service.modules.QuestionService;
import com.pdg.pymesbackend.service.validator.DimensionValidator;
import com.pdg.pymesbackend.service.validator.implementations.VersionValidatorImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    private QuestionMapper questionMapper;
    private OptionMapper optionMapper;
    private DimensionValidator dimensionValidator;
    private VersionValidatorImpl versionValidator;
    private LevelRepository levelRepository;

    @Override
    public Question createQuestion(QuestionDTO questionDTO) {

        //validar tag
        dimensionValidator.validateDimensionExists(questionDTO.getDimensionId());
        versionValidator.validateVersion(questionDTO.getVersionId());
        //validar companyType
        Question question = questionMapper.fromDTO(questionDTO);
        question.getRecommendation().setRecommendationId(UUID.randomUUID().toString());
        for(Step step : question.getRecommendation().getSteps()){
            step.setStepId(UUID.randomUUID().toString());
        }
        for(Option option : question.getOptions()){
            option.setOptionId(UUID.randomUUID().toString());
        }
        question.setQuestionId(UUID.randomUUID().toString());
        question.getRecommendation().setQuestionId(question.getQuestionId());
        question.setCompanyTypeId(companyTypeConstructor(questionDTO.getCompanyTypeId()).getCompanyTypeId());
        Question newQuestion = questionRepository.save(question);
        Level level = levelRepository.findById(questionDTO.getLevelId()).orElseThrow(() -> new PymeException(PymeExceptionType.LEVEL_NOT_FOUND));
        level.getQuestions().add(newQuestion.getQuestionId());
        levelRepository.save(level);
        return newQuestion;

    }

    @Override
    public void deleteQuestion(String id) {
        Question question = findById(id);
        questionRepository.delete(question);
    }

    private Question findById(String id) {
        return questionRepository.findById(id).orElseThrow(() -> new PymeException(PymeExceptionType.QUESTION_NOT_FOUND));
    }

    public QuestionOutDTO mapQuestionToOutDTO(Question question){
        return QuestionOutDTO.builder()
                .question(question.getQuestion())
                .questionId(question.getQuestionId())
                .options(question.getOptions())
                .answer(null)
                .maxScore(question.getScorePositive())
                .marked(false)
                .build();
    }

    public QuestionOutDTO mapAnswerToQuestionOutDTO(EvaluationResult evaluationResult){

        Question question = findById(evaluationResult.getQuestionId());
        Option answer = null;

        if(!Objects.equals(evaluationResult.getOptionId(), "")){
            answer = question.getOptions()
                    .stream()
                    .filter(option -> option.getOptionId().equals(evaluationResult.getOptionId()))
                    .findFirst().orElse(null);
        }
        return QuestionOutDTO.builder()
                .question(question.getQuestion())
                .questionId(question.getQuestionId())
                .options(question.getOptions())
                .answer(answer)
                .maxScore(question.getScorePositive())
                .marked(evaluationResult.isMarked())
                .build();
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

    @Override
    public List<Question> filterQuestionsByCompanyType(List<String> questionsId, String companyTypeId) {
        return questionRepository.findQuestionsByCompanyType(questionsId, companyTypeId);
    }

    @Override
    public List<Question> getQuestionsByLevel(String level) {
        Level levelObj = levelRepository.findById(level).orElseThrow(() -> new PymeException(PymeExceptionType.LEVEL_NOT_FOUND));
        List<String> questionsId = levelObj.getQuestions();
        return questionRepository.findAllById(questionsId);
    }

    private CompanyType companyTypeConstructor(Integer companyType){
        return switch (companyType) {
            case 1 -> CompanyType.builder()
                    .companyTypeId("MICRO")
                    .name("Micro Empresa")
                    .description("Menos de 10 empleados")
                    .build();
            case 2 -> CompanyType.builder()
                    .companyTypeId("PEQUENA")
                    .name("Pequeña Empresa")
                    .description("Entre 10 y 50 empleados")
                    .build();
            case 3 -> CompanyType.builder()
                    .companyTypeId("MEDIANA")
                    .name("Mediana Empresa")
                    .description("Entre 50 y 200 empleados")
                    .build();
            default -> null;
        };
    }
}
