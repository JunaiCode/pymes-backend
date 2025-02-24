package com.pdg.pymesbackend.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PymeExceptionType {

    MODEL_NOT_FOUND(101, "Model not found", "modelId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    TAG_NOT_FOUND(201, "Tag not found", "tagId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    TAG_ALREADY_EXISTS(202, "Tag already exists", "tagName", HttpStatus.CONFLICT, LogLevel.INFO),
    VERSION_ALREADY_EXISTS(302, "Version already exists", "versionId", HttpStatus.CONFLICT, LogLevel.INFO),
    DIMENSION_NOT_FOUND(401, "Dimension not found", "dimensionId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    DIMENSION_ALREADY_EXISTS(402, "Dimension already exists", "dimensionId", HttpStatus.CONFLICT, LogLevel.INFO),
    VERSION_NOT_FOUND(501, "Version not found", "versionId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    LEVEL_NOT_FOUND(601, "Level not found", "levelId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    LEVEL_ALREADY_EXISTS(602, "Level already exists", "levelId", HttpStatus.CONFLICT, LogLevel.INFO),
    QUESTION_NOT_FOUND(701, "Question not found", "questionId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    RECOMMENDATION_NOT_FOUND(801, "Recommendation not found", "recommendationId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    COMPANY_NOT_FOUND(901, "Company not found", "companyId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    EVALUATION_NOT_FOUND(1001, "Evaluation not found", "evaluationId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    EVALUATION_RESULT_NOT_FOUND(1002, "Evaluation result not found", "evaluationResultId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    EVALUATION_RESULT_ERROR(1003, "Error getting evaluation results", "evaluationId", HttpStatus.INTERNAL_SERVER_ERROR, LogLevel.INFO),
    EVALUATION_NOT_COMPLETED(1004, "Evaluation not completed", "evaluationId", HttpStatus.BAD_REQUEST, LogLevel.INFO),
    ACTION_PLAN_NOT_FOUND(1101, "Action plan not found", "actionPlanId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    INVALID_DATE_FORMAT(1102, "Invalid date format", "date", HttpStatus.BAD_REQUEST, LogLevel.INFO),
    RECOMMENDATION_ACTION_PLAN_NOT_FOUND(1103, "Recommendation action plan not found", "recommendationActionPlanId", HttpStatus.NOT_FOUND, LogLevel.INFO),
    COMPANY_ALREADY_EXISTS(1201, "Company already exists", "email", HttpStatus.CONFLICT, LogLevel.INFO),;
    private final int code;
    private final String message;
    private final String parameterName;
    private final HttpStatus responseStatus;
    private final LogLevel logLevel;
}
