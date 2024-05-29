package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.DimensionMapper;
import com.pdg.pymesbackend.matcher.DimensionMatcher;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.modules.implementations.DimensionServiceImpl;
import com.pdg.pymesbackend.service.validator.DimensionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DimensionServiceTest {

    @Mock
    private DimensionRepository dimensionRepository;
    @Mock
    private DimensionMapper dimensionMapper;

    @Mock
    private VersionService versionService;

    @Mock
    private DimensionValidator dimensionValidator;
    @InjectMocks
    private DimensionServiceImpl dimensionService;

    @Test
    public void testCreateDimension() {
        when(dimensionMapper.fromCreateDTO(createDimensionDTO())).thenReturn(createDimension());
        dimensionService.save(createDimensionDTO(), "1");
        Dimension dimension1 = createDimension();
        verify(dimensionRepository, times(1)).save(argThat(new DimensionMatcher(dimension1)));

    }

    @Test
    void testUpdateDimension() {
        when(dimensionValidator.validateDimensionExists("1")).thenReturn(createDimension());
        when(versionService.findVersionByDimensionId("1")).thenReturn(Version.builder().dimensions(List.of()).build());
        dimensionService.update("1", createDimensionDTO2());
        Dimension dimension1 = createDimension2();
        verify(dimensionRepository, times(1)).save(argThat(new DimensionMatcher(dimension1)));
    }

    @Test
    void testUpdateDimensionNotFound() {
        when(dimensionValidator.validateDimensionExists("1")).thenThrow(new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
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
        try {
            dimensionService.delete("1");
        } catch (Exception e) {
            verify(dimensionRepository, times(0)).deleteById("1");
            assert e.getMessage().equals("Dimension not found");
        }
    }

    @Test
    void testAddLevelToDimension() {
        when(versionService.findVersionByDimensionId("1")).thenReturn(Version.builder().dimensions(List.of()).build());
        when(dimensionRepository.findById("1")).thenReturn(Optional.of(createDimension()));
        ArgumentCaptor<Dimension> dimensionCaptor = ArgumentCaptor.forClass(Dimension.class);
        Dimension dimension = dimensionService.addLevelToDimension(Level.builder().name("level 1").build(), "1");
        verify(dimensionRepository, times(1)).save(dimensionCaptor.capture());
        assertEquals(1, dimensionCaptor.getValue().getLevels().size());
    }

    @Test
    void testAddLevelDimensionNotFound() {
        when(versionService.findVersionByDimensionId("1")).thenThrow(new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
        try {
            dimensionService.addLevelToDimension(Level.builder().name("level 1").build(), "1");
        } catch (Exception e) {
            verify(dimensionRepository, times(0)).save(any());
            assert e.getMessage().equals("Version not found");
        }
    }


    Dimension createDimension() {
        return Dimension.builder()
                .dimensionId("1")
                .name("Dimension Personas")
                .levels(List.of())
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
