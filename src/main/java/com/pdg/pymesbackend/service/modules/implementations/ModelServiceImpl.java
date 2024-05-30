package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.mapper.ModelMapper;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.ModelRepository;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.modules.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;
    private VersionRepository versionRepository;
    private VersionServiceImpl versionService;
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public Model save(ModelDTO model) {

        Model newModel = modelMapper.fromCreateDTO(model);
        deactivateActiveModel();
        newModel.setActive(true);

        newModel.setModelId(UUID.randomUUID().toString());
        newModel.setVersions(new ArrayList<>());
        return modelRepository.save(newModel);
    }

    @Override
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    @Override
    public Model findById(String id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Model not found"));
    }

    @Override
    public List<Version> findVersionsByModelId(String modelId) {
        List<String> versionIds = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Model not found"))
                .getVersions();
        if (versionIds == null || versionIds.isEmpty()) {
            return List.of();
        }else {
            return versionRepository.findVersionByVersionIdIn(versionIds);
        }
    }

    @Transactional
    @Override
    public Model addVersion(String modelId, VersionDTO version) {
        Model model = findById(modelId);
        deactivateActiveModel();
        Version saved = versionService.save(version);

        List<String> updatedVersions = new ArrayList<>(model.getVersions());
        updatedVersions.add(saved.getVersionId());
        model.setVersions(updatedVersions);
        model.setActive(true);
        return modelRepository.save(model);
    }

    @Override
    public String getActualVersion() {
        Model model = modelRepository.findActiveModel();
        if (model == null) {
            return null;
        }
        Version activeVersion = null;
        for (String versionId : model.getVersions()) {
            Version version = versionRepository.findById(versionId)
                    .orElseThrow(() -> new RuntimeException("Version not found"));
            if (version.isActive()) {
                activeVersion = version;
                return activeVersion.getVersionId();
            }
        }
        return null;

    }

    private void deactivateActiveVersion() {
        Version activeVersion = versionRepository.findActiveVersion();
        if (activeVersion != null) {
            activeVersion.setActive(false);
            versionRepository.save(activeVersion);
        }


    }

    private void deactivateActiveModel() {
        Model activeModel = modelRepository.findActiveModel();
        if (activeModel != null) {
            activeModel.setActive(false);

            modelRepository.save(activeModel);
        }

        Version activeVersion = versionRepository.findActiveVersion();
        if (activeVersion != null) {
            activeVersion.setActive(false);
            versionRepository.save(activeVersion);
        }
    }


}
