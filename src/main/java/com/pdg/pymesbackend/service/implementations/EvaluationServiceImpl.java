package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.EvaluationDTO;
import com.pdg.pymesbackend.mapper.EvaluationMapper;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.service.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private EvaluationMapper evaluationMapper;
    private EvaluationRepository evaluationRepository;
    @Override
    public Evaluation save(EvaluationDTO evaluationDTO) {
        Evaluation evaluation = evaluationMapper.fromEvaluationDTO(evaluationDTO);
        return evaluationRepository.save(evaluation);
    }
}
