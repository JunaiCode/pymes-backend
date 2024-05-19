package com.pdg.pymesbackend.service.validator.implementations;

import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.service.validator.DimensionValidator;
import org.springframework.stereotype.Service;

@Service
public class DimensionValidatorImpl implements DimensionValidator {

    private DimensionRepository dimensionRepository;
    @Override
    public Dimension validateDimensionExists(String dimensionId) {
        return dimensionRepository.findById(dimensionId)
                .orElseThrow(() -> new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
    }

}
