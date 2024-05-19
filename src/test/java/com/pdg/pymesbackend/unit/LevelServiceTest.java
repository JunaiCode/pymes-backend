package com.pdg.pymesbackend.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LevelServiceTest {
/*
    @Mock
    private LevelRepository levelRepository;

    @Mock
    private LevelMapper levelMapper;

    @InjectMocks
    private LevelServiceImpl levelService;

    @Test
    void testCreateLevel() {

        when(levelMapper.fromLevelDTO(createLevelDTO())).thenReturn(createLevel());
        levelService.save(createLevelDTO());
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
    }*/
}
