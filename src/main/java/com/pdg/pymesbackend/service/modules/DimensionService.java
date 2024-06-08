package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Level;

import java.util.List;

public interface DimensionService {

    Dimension save (DimensionDTO dimension, String versionId);
    Dimension update (String id, DimensionDTO dimension);
    void delete (String id);

    Dimension get (String id);
    List<Dimension> getAll ();


    Dimension addLevelToDimension(Level level, String dimensionId);
}
