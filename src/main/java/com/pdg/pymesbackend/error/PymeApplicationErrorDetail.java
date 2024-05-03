package com.pdg.pymesbackend.error;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Getter
@SuperBuilder
public class PymeApplicationErrorDetail extends PymeApplicationError{

    private final String detail;

}
