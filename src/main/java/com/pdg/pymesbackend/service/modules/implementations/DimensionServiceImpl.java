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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        newDimension.setDimensionId(UUID.randomUUID().toString());
        findByName(dimension.getName(), versionId);
        dimensionRepository.save(newDimension);
        versionService.addDimension(versionId, newDimension);
        return newDimension;

    }

    @Override
    public Dimension update(String id, DimensionDTO dimension) {
        Dimension oldDimension = dimensionValidator.validateDimensionExists(id);
        Version version = versionService.findVersionByDimensionId(id);
        List<Dimension> dimensions = new ArrayList<>(version.getDimensions());
        dimensions.remove(oldDimension);
        oldDimension.setName(dimension.getName());
        oldDimension.setDescription(dimension.getDescription());
        //Update the dimension in the version
        dimensions.add(oldDimension);
        version.setDimensions(dimensions);
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
    public Dimension addLevelToDimension(Level level, String dimensionId) {
        Version version = versionService.findVersionByDimensionId(dimensionId);
        Dimension dimension = findById(dimensionId);

        List<Dimension> dimensions = new ArrayList<>(version.getDimensions());
        dimensions.remove(dimension);

        List<Level> levels = new ArrayList<>(dimension.getLevels());
        levels.add(level);
        dimension.setLevels(levels);
        dimensions.add(dimension);
        version.setDimensions(dimensions);

        versionService.updateWithVersion(version);
        return dimensionRepository.save(dimension);
    }

    private void findByName(String name, String versionId) {
        versionService.findDimensionInVersionByName(versionId, name);
    }

    private Dimension findById(String id) {
        return dimensionRepository.findById(id)
                .orElseThrow(() -> new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
    }


}
