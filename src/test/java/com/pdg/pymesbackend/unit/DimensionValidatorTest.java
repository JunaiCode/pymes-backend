package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.service.modules.implementations.DimensionServiceImpl;
import com.pdg.pymesbackend.service.validator.implementations.DimensionValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DimensionValidatorTest {
    @Mock
    private DimensionRepository dimensionRepository;
    @InjectMocks
    private DimensionValidatorImpl dimensionValidator;

    @Test
    void testValidateDimension(){
        when(dimensionRepository.findById("1")).thenReturn(Optional.of(Dimension.builder().dimensionId("1").name("Dimension 1").build()));
        Dimension dimension = dimensionValidator.validateDimensionExists("1");

        assertEquals("1", dimension.getDimensionId());
        assertEquals("Dimension 1", dimension.getName());
    }

    @Test
    void testValidateDimensionNotFound(){
        try {
            dimensionValidator.validateDimensionExists("1");
        } catch (Exception e) {
            assertEquals("Dimension not found", e.getMessage());
        }
    }
}
