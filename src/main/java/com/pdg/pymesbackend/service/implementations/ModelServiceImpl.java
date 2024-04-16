package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.mapper.ModelMapper;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.repository.ModelRepository;
import com.pdg.pymesbackend.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;
    private ModelMapper modelMapper;

    @Override
    public Model save(ModelDTO model) {

        Model newModel = modelMapper.fromCreateDTO(model);
        newModel.setModelId(UUID.randomUUID().toString());
        return modelRepository.save(newModel);
    }
}
