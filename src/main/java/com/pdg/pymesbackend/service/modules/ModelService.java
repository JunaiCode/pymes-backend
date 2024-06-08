package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.model.Version;

import java.util.List;

public interface ModelService {

    Model save (ModelDTO model);

    List<Model> findAll();

    Model findById(String id);

    List<Version> findVersionsByModelId(String modelId);

    Model addVersion(String modelId, VersionDTO version);

    String getActualVersion();
}
