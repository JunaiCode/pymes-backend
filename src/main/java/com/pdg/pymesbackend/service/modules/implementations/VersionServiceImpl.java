package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.validator.VersionValidator;
import com.pdg.pymesbackend.service.validator.implementations.VersionValidatorImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VersionServiceImpl implements VersionService {

    private VersionRepository versionRepository;
    private VersionMapper versionMapper;
    private VersionValidatorImpl versionValidator;
    @Override
    public Version save(VersionDTO version) {
        Version newVersion = versionMapper.fromDTO(version);
        versionRepository.findByName(newVersion.getName())
                .ifPresent(existingVersion -> {
                    throw new PymeException(PymeExceptionType.VERSION_ALREADY_EXISTS);
                });
        return versionRepository.save(newVersion);
    }

    @Override
    public void addDimension(String versionId, Dimension newDimension) {
        Version version = versionValidator.validateVersion(versionId);
        version.getDimensions().add(newDimension);
        versionRepository.save(version);
    }

    @Override
    public Version get(String id) {
        return versionValidator.validateVersion(id);
    }

    @Override
    public void findDimensionInVersionByName(String versionId, String dimensionName) {
        Version version = versionValidator.validateVersion(versionId);
        //Checks if thhere is already a dimension with the same name in the version
        //if so, throws an exception, else, returns the dimension
        version.getDimensions().stream()
                .filter(dimension -> dimension.getName().equals(dimensionName))
                .findAny()
                .ifPresent(dimension -> {
                    throw new PymeException(PymeExceptionType.DIMENSION_ALREADY_EXISTS);
                });
    }

    @Override
    public Version findVersionByDimensionId(String dimensionId) {
        return versionRepository.findVersionByDimensionId(dimensionId)
                .orElseThrow(() -> new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
    }

    @Override
    public Version updateWithVersion(Version version) {
        return versionRepository.save(version);
    }

}
