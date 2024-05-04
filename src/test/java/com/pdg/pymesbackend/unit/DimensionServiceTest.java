package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.mapper.DimensionMapper;
import com.pdg.pymesbackend.matcher.DimensionMatcher;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.service.implementations.DimensionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DimensionServiceTest {

    @Mock
    private DimensionRepository dimensionRepository;
    @Mock
    private DimensionMapper dimensionMapper;
    @InjectMocks
    private DimensionServiceImpl dimensionService;

    @Test
    public void testCreateDimension() {
        when(dimensionMapper.fromCreateDTO(createDimensionDTO())).thenReturn(createDimension());
        dimensionService.save(createDimensionDTO());
        Dimension dimension1 = createDimension();
        verify(dimensionRepository, times(1)).save(argThat(new DimensionMatcher(dimension1)));

    }

    @Test
    void testCreateDimensionAlreadyExists() {
        when(dimensionRepository.findByName("Dimension Personas")).thenReturn(Optional.of(createDimension()));
        DimensionDTO dimensionDTO = createDimensionDTO();
        try {
            dimensionService.save(dimensionDTO);
        } catch (Exception e) {
            verify(dimensionRepository, times(0)).save(any());
            assert e.getMessage().equals("Dimension already exists");
        }
    }

    @Test
    void testUpdateDimension() {
        when(dimensionRepository.findById("1")).thenReturn(Optional.of(createDimension()));
        when(dimensionRepository.findByName("Dimension Gente")).thenReturn(Optional.empty());
        dimensionService.update("1", createDimensionDTO2());
        Dimension dimension1 = createDimension2();
        verify(dimensionRepository, times(1)).save(argThat(new DimensionMatcher(dimension1)));
    }

    @Test
    void testUpdateDimensionAlreadyExists() {
        when(dimensionRepository.findById("1")).thenReturn(Optional.of(createDimension()));
        when(dimensionRepository.findByName("Dimension Gente")).thenReturn(Optional.of(createDimension2()));
        DimensionDTO dimensionDTO = createDimensionDTO2();
        try {
            dimensionService.update("1", dimensionDTO);
        } catch (Exception e) {
            verify(dimensionRepository, times(0)).save(any());
            assert e.getMessage().equals("Dimension already exists");
        }
    }

    @Test
    void testUpdateDimensionNotFound() {
        when(dimensionRepository.findById("1")).thenReturn(Optional.empty());
        DimensionDTO dimensionDTO = createDimensionDTO2();
        try {
            dimensionService.update("1", dimensionDTO);
        } catch (Exception e) {
            verify(dimensionRepository, times(0)).save(any());
            assert e.getMessage().equals("Dimension not found");
        }
    }

    @Test
    void testGetDimension() {
        when(dimensionRepository.findById("1")).thenReturn(Optional.of(createDimension()));
        Dimension dimension = dimensionService.get("1");
        assertNotNull(dimension);
    }

    @Test
    void testGetDimensionNotFound() {
        when(dimensionRepository.findById("1")).thenReturn(Optional.empty());
        try {
            dimensionService.get("1");
        } catch (Exception e) {
            assert e.getMessage().equals("Dimension not found");
        }
    }

    @Test
    void testGetAllDimensions() {
        when(dimensionRepository.findAll()).thenReturn(List.of(createDimension()));
        List<Dimension> dimensions = dimensionService.getAll();
        assertEquals(1, dimensions.size());
    }

    @Test
    void testDeleteDimension() {
        when(dimensionRepository.findById("1")).thenReturn(Optional.of(createDimension()));
        dimensionService.delete("1");
        verify(dimensionRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteDimensionNotFound() {
        when(dimensionRepository.findById("1")).thenReturn(Optional.empty());
        try {
            dimensionService.delete("1");
        } catch (Exception e) {
            verify(dimensionRepository, times(0)).deleteById("1");
            assert e.getMessage().equals("Dimension not found");
        }
    }


    Dimension createDimension() {
        return Dimension.builder()
                .dimensionId("1")
                .name("Dimension Personas")
                .description("Description")
                .build();
    }

    Dimension createDimension2() {
        return Dimension.builder()
                .dimensionId("1")
                .name("Dimension Gente")
                .description("Description")
                .build();
    }

    DimensionDTO createDimensionDTO() {
        return DimensionDTO.builder()
                .name("Dimension Personas")
                .description("Description")
                .build();
    }

    DimensionDTO createDimensionDTO2() {
        return DimensionDTO.builder()
                .name("Dimension Gente")
                .description("Description")
                .build();
    }
}
