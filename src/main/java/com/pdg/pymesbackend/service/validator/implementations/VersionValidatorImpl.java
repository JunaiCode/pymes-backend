package com.pdg.pymesbackend.service.validator.implementations;

import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.validator.VersionValidator;
import org.springframework.stereotype.Service;

@Service
public class VersionValidatorImpl implements VersionValidator {
    private VersionRepository versionRepository;
    @Override
    public Version validateVersion(String versionId) {
        return versionRepository.findById(versionId)
                .orElseThrow(() -> new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
    }
}
