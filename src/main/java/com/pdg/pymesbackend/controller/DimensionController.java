package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.DimensionAPI;
import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.service.modules.DimensionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class DimensionController implements DimensionAPI {
    private final DimensionService dimensionService;
    @Override
    public Dimension createDimension(DimensionDTO dimension,String versionId) {

        return dimensionService.save(dimension, versionId);
    }

    @Override
    public Dimension getDimension(String id) {
        return dimensionService.get(id);
    }

    @Override
    public List<Dimension> getAllDimensions() {
        return null;
    }

    @Override
    public Dimension updateDimension(DimensionDTO dimension, String id) {
        return dimensionService.update(id, dimension);
    }

    @Override
    public void deleteDimension(String id) {
        dimensionService.delete(id);
    }

    @Override
    public Dimension addLevelToDimension(LevelDTO level, String id) {
        return dimensionService.addLevelToDimension(level,id);
    }
}
