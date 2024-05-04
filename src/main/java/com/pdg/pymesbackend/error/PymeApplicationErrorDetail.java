package com.pdg.pymesbackend.error;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
@Getter
@SuperBuilder
public class PymeApplicationErrorDetail extends PymeApplicationError{

    private final String detail;

}
