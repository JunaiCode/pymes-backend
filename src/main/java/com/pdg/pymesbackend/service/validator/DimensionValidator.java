package com.pdg.pymesbackend.service.validator;

import com.pdg.pymesbackend.model.Dimension;

public interface DimensionValidator {

    Dimension validateDimensionExists(String dimensionId);


}
