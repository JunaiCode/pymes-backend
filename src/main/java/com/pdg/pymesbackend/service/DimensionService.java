package com.pdg.pymesbackend.service;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.model.Dimension;

public interface DimensionService {

    Dimension save (DimensionDTO dimension);
}
