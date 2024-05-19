package com.pdg.pymesbackend.service.validator;

import com.pdg.pymesbackend.model.Version;

public interface VersionValidator {

    Version validateVersion(String versionId);
}
