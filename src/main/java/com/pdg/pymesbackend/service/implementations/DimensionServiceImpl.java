package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.DimensionMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Model;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.service.DimensionService;
import com.pdg.pymesbackend.service.ModelService;
import com.pdg.pymesbackend.service.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DimensionServiceImpl implements DimensionService {

    private DimensionRepository dimensionRepository;
    private DimensionMapper dimensionMapper;
    private VersionService versionService;

    @Override
    public Dimension save(DimensionDTO dimension, String versionId) {
        Dimension newDimension = dimensionMapper.fromCreateDTO(dimension);
        findByName(dimension.getName());
        dimensionRepository.save(newDimension);
        versionService.addDimension(versionId, newDimension);
        return newDimension;

    }

    @Override
    public Dimension update(String id, DimensionDTO dimension) {
        Dimension oldDimension = dimensionRepository.findById(id)
                .orElseThrow(() -> new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
        findByName(dimension.getName());
        oldDimension.setName(dimension.getName());
        oldDimension.setDescription(dimension.getDescription());
        return dimensionRepository.save(oldDimension);
    }
    @Override
    public void delete(String id) {
        dimensionRepository.findById(id)
                .orElseThrow(() -> new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
        dimensionRepository.deleteById(id);
    }

    @Override
    public Dimension get(String id) {
        return findById(id);
    }

    @Override
    public List<Dimension> getAll() {
        return dimensionRepository.findAll();
    }



    private void findByName(String name) {
        dimensionRepository.findByName(name)
                .ifPresent(existingDimension -> {
                    throw new PymeException(PymeExceptionType.DIMENSION_ALREADY_EXISTS);
                });
    }

    private Dimension findById(String id) {
        return dimensionRepository.findById(id)
                .orElseThrow(() -> new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
    }


}
