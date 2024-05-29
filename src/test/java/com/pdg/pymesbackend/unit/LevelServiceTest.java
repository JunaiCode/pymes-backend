package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.mapper.LevelMapper;
import com.pdg.pymesbackend.matcher.LevelMatcher;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.repository.LevelRepository;
import com.pdg.pymesbackend.service.modules.implementations.DimensionServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.LevelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LevelServiceTest {

    @Mock
    private LevelRepository levelRepository;

    @Mock
    DimensionServiceImpl dimensionService;

    @Mock
    private LevelMapper levelMapper;

    @InjectMocks
    private LevelServiceImpl levelService;

    @Test
    void testCreateLevel() {

        when(levelMapper.fromLevelDTO(createLevelDTO())).thenReturn(createLevel());
        when(dimensionService.get("1")).thenReturn(Dimension.builder().levels(List.of()).build());
        levelService.save(createLevelDTO(), "1");
        Level level1 = createLevel();
        verify(levelRepository, times(1)).save(argThat(new LevelMatcher(level1)));

    }

    @Test
    void testCreateLevelAlreadyExists() {
        when(levelMapper.fromLevelDTO(createLevelDTO())).thenReturn(createLevel());
        when(dimensionService.get("1")).thenReturn(Dimension.builder().levels(List.of(createLevel())).build());
        try {
            levelService.save(createLevelDTO(), "1");
        } catch (Exception e) {
            verify(levelRepository, times(0)).save(any());
            assertEquals("Level already exists", e.getMessage());
        }
    }

    @Test
    void testGetLevelsInDimension() {
        when(dimensionService.get("1")).thenReturn(Dimension.builder().levels(List.of(createLevel())).build());
        List<Level> levels = levelService.getLevelsInDimension("1");
        assertEquals(1, levels.size());
    }

    @Test
    void testUpdateQuestions() {
        when(levelRepository.findById("1")).thenReturn(java.util.Optional.of(createLevel()));
        levelService.updateQuestions("1", List.of("2"));
        Level level1 = createLevel();
        level1.setQuestions(List.of("2"));
        verify(levelRepository, times(1)).save(argThat(new LevelMatcher(level1)));
    }

    @Test
    void testUpdateQuestionsNotFound() {
        when(levelRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            levelService.updateQuestions("1", List.of("2"));
        } catch (Exception e) {
            verify(levelRepository, times(0)).save(any());
            assertEquals("Level not found", e.getMessage());
        }
    }

    @Test
    void testGetLevel() {
        when(levelRepository.findById("1")).thenReturn(java.util.Optional.of(createLevel()));
        Level level = levelService.getLevel("1");
        assertEquals(createLevel(), level);
    }

    @Test
    void testGetLevelNotFound() {
        when(levelRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            levelService.getLevel("1");
        } catch (Exception e) {
            assertEquals("Level not found", e.getMessage());
        }
    }

    Level createLevel() {
        return Level.builder()
                .levelId("1")
                .value(1)
                .questions(List.of())
                .name("Level 1")
                .description("Level 1 description")
                .build();
    }

    LevelDTO createLevelDTO() {
        return LevelDTO.builder()
                .name("Level 1")
                .description("Level 1 description")
                .build();
    }
}
