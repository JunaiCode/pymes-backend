package com.pdg.pymesbackend.api;


import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.model.Version;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/model")
public interface ModelAPI {

    @PostMapping("/add")
    Model createModel(@RequestBody @Valid ModelDTO model);

    @GetMapping("/get/all")
    List<Model> getAll();

    @GetMapping("/get/{id}")
    Model getById(@PathVariable("id") String id);

    @GetMapping("/get/versions/{modelId}")
    List<Version> getVersionsByModelId(@PathVariable("modelId") String modelId);

}
