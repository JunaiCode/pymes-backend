package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VersionServiceImpl implements VersionService {

    private VersionRepository versionRepository;
    private VersionMapper versionMapper;
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
        Version version = versionRepository.findById(versionId)
                .orElseThrow(() -> new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
        version.getDimensions().add(newDimension);
        versionRepository.save(version);
    }

    @Override
    public Version get(String id) {
        return versionRepository.findById(id)
                .orElseThrow(() -> new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
    }

}
