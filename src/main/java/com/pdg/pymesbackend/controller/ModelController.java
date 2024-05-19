package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.ModelAPI;
import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.service.modules.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ModelController implements ModelAPI {

    private final ModelService modelService;

    @Override
    public Model createModel(ModelDTO model) {
        return modelService.save(model);
    }

    @Override
    public Model addVersion(VersionDTO version, String modelId) {
        return modelService.addVersion(modelId, version);
    }

    @Override
    public List<Model> getAll() {
        return modelService.findAll();
    }

    @Override
    public Model getById(String id) {
        return modelService.findById(id);
    }

    @Override
    public List<Version> getVersionsByModelId(String modelId) {
        return modelService.findVersionsByModelId(modelId);
    }
}
