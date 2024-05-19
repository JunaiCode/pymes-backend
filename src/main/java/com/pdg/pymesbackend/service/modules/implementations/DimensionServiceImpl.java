package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.DimensionMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.service.modules.DimensionService;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.validator.DimensionValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DimensionServiceImpl implements DimensionService {

    private DimensionRepository dimensionRepository;
    private DimensionMapper dimensionMapper;
    private DimensionValidator dimensionValidator;
    private VersionService versionService;

    @Override
    public Dimension save(DimensionDTO dimension, String versionId) {
        Dimension newDimension = dimensionMapper.fromCreateDTO(dimension);
        findByName(dimension.getName(), versionId);
        dimensionRepository.save(newDimension);
        versionService.addDimension(versionId, newDimension);
        return newDimension;

    }

    @Override
    public Dimension update(String id, DimensionDTO dimension) {
        Dimension oldDimension = dimensionValidator.validateDimensionExists(id);
        Version version = versionService.findVersionByDimensionId(id);
        version.getDimensions().remove(oldDimension);
        oldDimension.setName(dimension.getName());
        oldDimension.setDescription(dimension.getDescription());
        //Update the dimension in the version
        version.getDimensions().add(oldDimension);
        versionService.updateWithVersion(version);
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

    @Override
    public void addLevelToDimension(Level level, String dimensionId) {
        Version version = versionService.findVersionByDimensionId(dimensionId);
        Dimension dimension = findById(dimensionId);
        version.getDimensions().remove(dimension);
        dimension.getLevels().add(level);
        version.getDimensions().add(dimension);
        versionService.updateWithVersion(version);
        dimensionRepository.save(dimension);
    }

    private void findByName(String name, String versionId) {
        versionService.findDimensionInVersionByName(versionId, name);
    }

    private Dimension findById(String id) {
        return dimensionRepository.findById(id)
                .orElseThrow(() -> new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
    }


}
