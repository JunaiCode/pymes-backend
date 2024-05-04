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
    VERSION_NOT_FOUND(501, "Version not found", "versionId", HttpStatus.NOT_FOUND, LogLevel.INFO),;
    private final int code;
    private final String message;
    private final String parameterName;
    private final HttpStatus responseStatus;
    private final LogLevel logLevel;
}
