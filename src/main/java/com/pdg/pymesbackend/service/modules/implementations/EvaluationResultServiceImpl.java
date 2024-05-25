package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.EvaluationResultMapper;
import com.pdg.pymesbackend.model.EvaluationResult;
import com.pdg.pymesbackend.repository.EvaluationResultRepository;
import com.pdg.pymesbackend.service.modules.EvaluationResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EvaluationResultServiceImpl implements EvaluationResultService {

    private EvaluationResultRepository evaluationResultRepository;
    private EvaluationResultMapper evaluationResultMapper;
    @Override
    public EvaluationResult save(EvaluationResultDTO evaluationResultDTO) {
        EvaluationResult evaluationResult = evaluationResultMapper.fromDTO(evaluationResultDTO);
        evaluationResult.setEvaluationResultId(UUID.randomUUID().toString());
        return evaluationResultRepository.save(evaluationResult);
    }

    @Override
    public EvaluationResult findById(String id) {
        return evaluationResultRepository.findById(id).orElseThrow(()-> new PymeException(PymeExceptionType.EVALUATION_RESULT_NOT_FOUND));
    }

    public List<EvaluationResult> saveAll(List<EvaluationResultDTO> evaluationResults){
        List<EvaluationResult> newResults = evaluationResults.stream()
                .map(evaluationResultMapper::fromDTO)
                .toList();
        newResults.forEach(evaluationResult -> evaluationResult.setEvaluationResultId(UUID.randomUUID().toString()));
        return evaluationResultRepository.saveAll(newResults);
    }

    public void deleteAllById(List<String> evaluationResultId){
        evaluationResultRepository.deleteAllById(evaluationResultId);
    }

    @Override
    public List<EvaluationResult> getEvaluationResults(List<String> evaluationResultId) {
        return evaluationResultRepository.findAllById(evaluationResultId);
    }

}
