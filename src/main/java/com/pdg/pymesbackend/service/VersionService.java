package com.pdg.pymesbackend.service;


import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Version;

public interface VersionService {

    Version save(VersionDTO version);

    void addDimension(String versionId, Dimension newDimension);

    Version get(String id);

    void findDimensionInVersionByName(String versionId, String dimensionName);

    Version findVersionByDimensionId(String dimensionId);

    Version updateWithVersion(Version version);
}
