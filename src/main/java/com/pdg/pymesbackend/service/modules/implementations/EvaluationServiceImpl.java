package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private EvaluationRepository evaluationRepository;
    private QuestionServiceImpl questionService;
    private EvaluationResultServiceImpl evaluationResultService;
    private CompanyServiceImpl companyService;

    @Override
    public Evaluation save(String companyId) {

        Evaluation evaluation =  evaluationRepository.save(Evaluation.builder()
                .evaluationId(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .questionResults(List.of())
                .dimensionResults(List.of())
                .completed(false)
                .build());

        companyService.addEvaluationToCompany(companyId, evaluation.getEvaluationId());
        return evaluation;
    }

    @Override
    public void finishEvaluation(String evaluationId){
        Evaluation  evaluation = getEvaluationById(evaluationId);

        //obtener todos los resultados

        List<EvaluationResult> results = evaluation.getQuestionResults().stream()
                .map(evaluationResultService::findById)
                .toList();


        //validar que todas las preguntas tengan respuesta
        boolean evaluationComplete = results
                .stream()
                .allMatch(evaluationResult -> evaluationResult.getOptionId()!=null);

        if(!evaluationComplete){
            throw new PymeException(PymeExceptionType.EVALUATION_NOT_COMPLETED);
        }else {
            evaluation.setCompleted(true);
            evaluationRepository.save(evaluation);

        }
    }

    private List<Recommendation> getRecommendations(List<EvaluationResult> evaluationResults){

        List<Recommendation> recommendations = new ArrayList<>();

        for (EvaluationResult evaluationResult : evaluationResults) {
            //obtener pregunta
            Question question = questionService.getQuestion(evaluationResult.getQuestionId());
            boolean passed = false;
            //obtener valor de pregunta seleccionada y puntaje de aprobación
            int selected = question.getOptions()
                    .stream()
                    .filter(option -> option.getOptionId().equals(evaluationResult.getOptionId()))
                    .toList()
                    .get(0)
                    .getValue();
            int scorePassed = question.getScorePositive();
            if (selected >= scorePassed) {
                passed = true;
            }
            if(passed){
                recommendations.add(question.getRecommendation());
            }
        }

        return recommendations;

    }

    private List<RecommendationActionPlan> getRecommendationTracking(List<Recommendation> recommendations){

        return  recommendations
                .stream()
                .flatMap(recommendation -> recommendation.getSteps()
                        .stream()
                        .map(step -> RecommendationActionPlan.builder()
                                .recommendationActionPlanId(UUID.randomUUID().toString())
                                .recommendationId(recommendation.getRecommendationId())
                                .completed(false)
                                .step(step)
                                .date(null)
                                .build()))
                .toList();

    }

    @Override
    public Map<String, List<QuestionOutDTO>> checkUncompletedEvaluation(String companyId){
        Company company = companyService.getCompanyById(companyId);
        List<String> evaluations = company.getEvaluations();
        if(evaluations.isEmpty()){
            return null;
        }else {
            String evaluationId = evaluations.get(evaluations.size()-1);
            Evaluation evaluation = getEvaluationById(evaluationId);
            if(evaluation.isCompleted()){
                return null;
            }else {
                return getEvaluationResults(evaluation);
            }
        }

    }

    private Map<String, List<QuestionOutDTO>> getEvaluationResults(Evaluation evaluation) {
        //get results
        List<EvaluationResult> evaluationResult = evaluationResultService.getEvaluationResults(evaluation.getQuestionResults());
        //organize by dimension
        Map<String, List<EvaluationResult>> resultsByDimension = evaluationResult
                .stream()
                .collect(Collectors.groupingBy(EvaluationResult::getDimensionId));

        //map them to out entity

        return resultsByDimension.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                        .stream().map(result -> questionService.mapAnswerToQuestionOutDTO(result)).toList()));
    }

    @Override
    public Evaluation getEvaluationById(String evaluationId) {
        return evaluationRepository.findById(evaluationId).orElseThrow(() -> new PymeException(PymeExceptionType.EVALUATION_NOT_FOUND));
    }

    @Override
    public List<EvaluationResult> addEvaluationResults(String evaluationId, List<EvaluationResultDTO> answers) {
        Evaluation evaluation = getEvaluationById(evaluationId);
        //obtener antiguas respuestas
        List<String> oldAnswers = evaluation.getQuestionResults();

        //crear nuevas respuestas
        List<EvaluationResult> newResults = evaluationResultService.saveAll(answers);
        List<String> evaluationResultsIds = newResults.stream().map(EvaluationResult::getEvaluationResultId).toList();

        //settear nuevas respuestas
        evaluation.setQuestionResults(evaluationResultsIds);
        evaluationRepository.save(evaluation);
        //borrar antiguas respuestas
        evaluationResultService.deleteAllById(oldAnswers);

        return newResults;
    }

    @Override
    public void addActionPlanToEvaluation(String evaluationId, String actionPlanId){
        Evaluation evaluation = getEvaluationById(evaluationId);
        evaluation.setActionPlanId(actionPlanId);
        evaluationRepository.save(evaluation);
    }


}
