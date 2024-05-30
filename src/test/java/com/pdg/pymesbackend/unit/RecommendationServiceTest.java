package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.dto.StepDTO;
import com.pdg.pymesbackend.mapper.RecommendationMapper;
import com.pdg.pymesbackend.model.Recommendation;
import com.pdg.pymesbackend.model.Step;
import com.pdg.pymesbackend.repository.RecommendationRepository;
import com.pdg.pymesbackend.service.modules.implementations.RecommendationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private RecommendationRepository recommendationRepository;
    @Mock
    private RecommendationMapper recommendationMapper;
    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Test
    void testCreateRecommendation(){
        when(recommendationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(recommendationMapper.fromDTO(any())).thenReturn(createRecommendation());
        recommendationService.createRecommendation(createRecommendationDTO());
        verify(recommendationRepository, times(1)).save(createRecommendation());
    }

    @Test
    void testGetRecommendationById(){
        when(recommendationRepository.findById("1")).thenReturn(java.util.Optional.of(createRecommendation()));
        Recommendation result = recommendationService.getRecommendationById("1");
        assertNotNull(result);
        assertEquals(result.getDescription(), "Recommendation 1");
    }

    @Test
    void testGetRecommendationByIdNotFound(){
        when(recommendationRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            recommendationService.getRecommendationById("1");
        }
        catch (Exception e) {
            assertEquals(e.getMessage(), "Recommendation not found");
        }

    }

    Recommendation createRecommendation(){
        return Recommendation.builder()
                .description("Recommendation 1")
                .steps(List.of(
                        Step.builder().description("Step 1").build(),
                        Step.builder().description("Step 2").build()
                ))
                .build();
    }

    RecommendationDTO createRecommendationDTO(){
        return RecommendationDTO.builder()
                .description("Recommendation 1")
                .steps(List.of(
                        StepDTO.builder().description("Step 1").build(),
                        StepDTO.builder().description("Step 2").build()
                ))
                .build();
    }
}
