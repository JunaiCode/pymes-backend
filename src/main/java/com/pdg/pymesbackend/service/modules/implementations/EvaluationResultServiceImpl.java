package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.EvaluationResultOutDTO;
import com.pdg.pymesbackend.mapper.EvaluationResultMapper;
import com.pdg.pymesbackend.model.EvaluationResult;
import com.pdg.pymesbackend.model.Option;
import com.pdg.pymesbackend.model.Question;
import com.pdg.pymesbackend.repository.EvaluationResultRepository;
import com.pdg.pymesbackend.service.modules.EvaluationResultService;
import com.pdg.pymesbackend.service.modules.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EvaluationResultServiceImpl implements EvaluationResultService {

    private EvaluationResultRepository evaluationResultRepository;
    private EvaluationResultMapper evaluationResultMapper;
    private QuestionService questionService;
    @Override
    public EvaluationResult save(EvaluationResultDTO evaluationResultDTO) {
        EvaluationResult evaluationResult = evaluationResultMapper.fromDTO(evaluationResultDTO);
        evaluationResult.setEvaluationResultId(UUID.randomUUID().toString());
        return evaluationResultRepository.save(evaluationResult);
    }

    @Override
    public List<EvaluationResult> getEvaluationResults(List<String> evaluationResultId) {
        return evaluationResultRepository.findAllById(evaluationResultId);
    }

    public EvaluationResultOutDTO mapToOutDTO(EvaluationResult evaluationResult){
        Question question = questionService.getQuestion(evaluationResult.getQuestionId());

        Option answer = null;

        if(!Objects.equals(evaluationResult.getOptionId(), "")){
            answer = question.getOptions()
                    .stream()
                    .filter(option -> option.getOptionId().equals(evaluationResult.getOptionId()))
                    .findFirst().orElse(null);
        }
        return EvaluationResultOutDTO.builder()
                .question(question.getQuestion())
                .options(question.getOptions())
                .answer(answer)
                .build();
    }
}
