package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.matcher.VersionMatcher;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.implementations.VersionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VersionServiceTest {
    /*
    @Mock
    private VersionRepository versionRepository;
    @Mock
    private VersionMapper versionMapper;
    @InjectMocks
    private VersionServiceImpl versionService;

    @Test
    void testCreateVersion() {
        when(versionMapper.fromDTO(createVersionDTO())).thenReturn(createVersion());
        versionService.save(createVersionDTO());
        Version version1 = createVersion();
        verify(versionRepository, times(1)).save(argThat(new VersionMatcher(version1)));
    }

    @Test
    void testCreateVersionWithExistingVersion() {
        when(versionMapper.fromDTO(createVersionDTO())).thenReturn(createVersion());
        when(versionRepository.findByName("Version 1")).thenReturn(java.util.Optional.of(createVersion()));
        try {
            versionService.save(createVersionDTO());
        } catch (Exception e) {
            verify(versionRepository, times(0)).save(any());
            assertEquals("Version already exists", e.getMessage());
        }
    }

    Version createVersion() {
        return Version.builder()
                .versionId("1")
                .name("Version 1")
                .levels(new ArrayList<>())
                .dimensions(new ArrayList<>())
                .build();
    }

    VersionDTO createVersionDTO() {
        return VersionDTO.builder()
                .name("Version 1")
                .build();
    }*/
}
