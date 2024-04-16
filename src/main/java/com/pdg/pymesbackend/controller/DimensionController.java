package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.DimensionAPI;
import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.service.DimensionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DimensionController implements DimensionAPI {
    private final DimensionService dimensionService;
    @Override
    public Dimension createDimension(DimensionDTO dimension) {
        return dimensionService.save(dimension);
    }
}
