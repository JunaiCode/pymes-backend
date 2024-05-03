package com.pdg.pymesbackend.error;

import lombok.Getter;

@Getter
public class PymeException extends RuntimeException{
    private final PymeExceptionType pymeExceptionType;

    public PymeException(PymeExceptionType pymeExceptionType) {
        super(pymeExceptionType.getMessage());
        this.pymeExceptionType = pymeExceptionType;
    }
}
