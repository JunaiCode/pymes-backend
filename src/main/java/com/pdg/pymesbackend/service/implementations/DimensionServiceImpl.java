package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.mapper.DimensionMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.service.DimensionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DimensionServiceImpl implements DimensionService {

    private DimensionRepository dimensionRepository;
    private DimensionMapper dimensionMapper;
    @Override
    public Dimension save(DimensionDTO dimension) {
        Dimension newDimension = dimensionMapper.fromCreateDTO(dimension);
        return dimensionRepository.save(newDimension);
    }
}
