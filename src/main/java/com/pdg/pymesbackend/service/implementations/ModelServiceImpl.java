package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.mapper.ModelMapper;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.ModelRepository;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;
    private VersionRepository versionRepository;
    private VersionMapper  versionMapper;
    private ModelMapper modelMapper;

    @Override
    public Model save(ModelDTO model) {

        Model newModel = modelMapper.fromCreateDTO(model);
        newModel.setModelId(UUID.randomUUID().toString());
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

    @Override
    public Model addVersion(String modelId, VersionDTO version) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        //Refactor this using version service
        Version newVersion = versionMapper.fromDTO(version);
        newVersion.setVersionId(UUID.randomUUID().toString());
        Version saved = versionRepository.save(newVersion);
        List<String> updatedVersions = new ArrayList<>(model.getVersions());
        updatedVersions.add(saved.getVersionId());
        model.setVersions(updatedVersions);
        return modelRepository.save(model);
    }



}
