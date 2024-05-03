package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.VersionMapper;
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

}
