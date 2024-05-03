package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.mapper.LevelMapper;
import com.pdg.pymesbackend.matcher.LevelMatcher;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.repository.LevelRepository;
import com.pdg.pymesbackend.service.implementations.LevelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LevelServiceTest {

    @Mock
    private LevelRepository levelRepository;

    @Mock
    private LevelMapper levelMapper;

    @InjectMocks
    private LevelServiceImpl levelService;

    @Test
    void testCreateLevel() {

        when(levelMapper.fromLevelDTO(createLevelDTO())).thenReturn(createLevel());
        Level level = levelService.save(createLevelDTO());
        Level level1 = createLevel();
        verify(levelRepository, times(1)).save(argThat(new LevelMatcher(level1)));

    }

    Level createLevel() {
        return Level.builder()
                .levelId("1")
                .name("Level 1")
                .description("Level 1 description")
                .scoreMin(0)
                .scoreMax(15)
                .build();
    }

    LevelDTO createLevelDTO() {
        return LevelDTO.builder()
                .name("Level 1")
                .description("Level 1 description")
                .scoreMin(0)
                .scoreMax(15)
                .build();
    }
}
