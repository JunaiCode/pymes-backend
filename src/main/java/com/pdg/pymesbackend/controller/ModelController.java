package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.ModelAPI;
import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ModelController implements ModelAPI {

    private final ModelService modelService;

    @Override
    public Model createModel(ModelDTO model) {
        return modelService.save(model);
    }
}
