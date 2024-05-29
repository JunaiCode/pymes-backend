package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.EvaluationResultDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private VersionServiceImpl versionService;

    @Override
    public Evaluation save(String companyId) {

        return evaluationRepository.save(Evaluation.builder()
                .evaluationId(UUID.randomUUID().toString())
                .date(LocalDateTime.now())
                .questionResults(List.of())
                .dimensionResults(List.of())
                .completed(false)
                .build());
    }

    @Override
    public void finishEvaluation(String evaluationId, String versionId){
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
            List<DimensionResult> dimensionResults = calculateLevel(evaluation, versionId);
            evaluation.setDimensionResults(dimensionResults);
            evaluationRepository.save(evaluation);

        }
    }

    private List<DimensionResult> calculateLevel(Evaluation evaluation, String versionId){

        //separar las respuestas por dimension
        Map<String, List<EvaluationResult>> resultsByDimension = evaluation.getQuestionResults()
                .stream()
                .map(evaluationResultService::findById)
                .collect(Collectors.groupingBy(EvaluationResult::getDimensionId));

        //obtener las dimensiones de la versión

        List<Dimension> dimensions = versionService.get(versionId).getDimensions();

        //calcular nivel por dimensión

        return dimensions.stream()
                .map(dimension -> calculateDimension(dimension, resultsByDimension.get(dimension.getDimensionId())))
                .toList();

    }

    private DimensionResult calculateDimension(Dimension dimension, List<EvaluationResult> answers){

        int approvedLevel = 0;
        boolean continueCalculating = true;

        for(int i = 0; i < dimension.getLevels().size() && continueCalculating; i++){
            Level level = dimension.getLevels().get(i);

            //obtener las preguntas del nivel
            List<String> questions = level.getQuestions();
            //determinar cuales preguntas fueron aprobadas
            Map<String, Boolean> approvedAnswers = getApprovedQuestions(answers);
            if(allQuestionsApproved(questions, approvedAnswers)){
                approvedLevel++;
            }else {
                continueCalculating = false;
            }
        }

        if(approvedLevel < dimension.getLevels().size()){
            approvedLevel++;
        }
        final int approvedLevelFinal = approvedLevel;


        Level level = dimension.getLevels()
                .stream()
                .filter(l -> l.getValue() == approvedLevelFinal)
                .toList()
                .get(0);

        return DimensionResult.builder()
                .dimensionId(dimension.getDimensionId())
                .dimensionName(dimension.getName())
                .levelId(level.getLevelId())
                .levelName(level.getName())
                .levelValue(level.getValue())
                .build();
    }

    private Map<String, Boolean> getApprovedQuestions(List<EvaluationResult> answers){

        Map<String, Boolean> approvedQuestions = new HashMap<>();

        for (EvaluationResult evaluationResult : answers) {
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
            approvedQuestions.put(question.getQuestionId(), passed);
        }

        return approvedQuestions;
    }

    private boolean allQuestionsApproved(List<String> levelQuestions, Map<String, Boolean> approved){
        int approvedQuestions = 0;
        for(String question: levelQuestions){
            if(approved.containsKey(question) && approved.get(question)){
                approvedQuestions++;
            }
        }
        return approvedQuestions == levelQuestions.size();
    }



    @Override
    public OnGoingEvaluationOutDTO checkUncompletedEvaluation(Company company){
        List<String> evaluations = company.getEvaluations();
        if(evaluations.isEmpty()){
            return null;
        }else {
            //tomar última evaluación
            String evaluationId = evaluations.get(evaluations.size()-1);
            Evaluation evaluation = getEvaluationById(evaluationId);
            if(evaluation.isCompleted()){
                return null;
            }else {
                List<EvaluationResult> evaluationResult = evaluationResultService.getEvaluationResults(evaluation.getQuestionResults());

                List<QuestionOutDTO> answers = evaluationResult.stream().map(result -> questionService.mapAnswerToQuestionOutDTO(result)).toList();

                return OnGoingEvaluationOutDTO.builder()
                        .evaluationId(evaluationId)
                        .questions(answers)
                        .build();
            }
        }

    }

    @Override
    public Evaluation getRecentCompletedEvaluation(List<String> evaluations){
        if(evaluations.isEmpty()){
            return null;
        }else {
            Collections.reverse(evaluations);
            return evaluations
                    .stream()
                    .map(this::getEvaluationById)
                    .filter(Evaluation::isCompleted)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public Evaluation getEvaluationById(String evaluationId) {
        return evaluationRepository.findById(evaluationId).orElseThrow(() -> new PymeException(PymeExceptionType.EVALUATION_NOT_FOUND));
    }

    @Override
    public List<Evaluation> getCompletedEvaluationsByIds(List<String> evaluationsIds){
        return evaluationRepository.findAllByEvaluationIdAndCompleted(evaluationsIds, true);
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

        //bug: no se borran las respuestas antiguas del repositorio TODO
        //borrar antiguas respuestas
        //oldAnswers.forEach(answer -> evaluationResultService.deleteById(answer));
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
