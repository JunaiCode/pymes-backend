package com.pdg.pymesbackend.service;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.model.Version;

import java.util.List;

public interface ModelService {

    Model save (ModelDTO model);

    List<Model> findAll();

    Model findById(String id);

    List<Version> findVersionsByModelId(String modelId);
}
