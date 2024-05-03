package com.pdg.pymesbackend.service;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.model.Dimension;

import java.util.List;

public interface DimensionService {

    Dimension save (DimensionDTO dimension);
    Dimension update (String id, DimensionDTO dimension);
    void delete (String id);

    Dimension get (String id);
    List<Dimension> getAll ();
}
