package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.DimensionQuestionInDTO;
import com.pdg.pymesbackend.dto.out.DimensionQuestionOutDTO;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.model.Question;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.modules.implementations.QuestionServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.VersionServiceImpl;
import com.pdg.pymesbackend.service.validator.implementations.VersionValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VersionServiceTest {

    @Mock
    private VersionRepository versionRepository;
    @Mock
    private VersionMapper versionMapper;

    @Mock
    private QuestionServiceImpl questionService;

    @Mock
    private VersionValidatorImpl versionValidator;
    @InjectMocks
    private VersionServiceImpl versionService;

    @Test
    void testGetDimensionLevelQuestions(){

        DimensionQuestionInDTO data = DimensionQuestionInDTO.builder()
                .companyTypeId("1")
                .dimensionId("1")
                .levelId("1")
                .versionId("1")
                .build();
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        List<DimensionQuestionOutDTO> result = versionService.getDimensionLevelQuestions(data);
        assertEquals(0, result.get(0).getQuestions().size());

    }

    Version createVersion() {
        return Version.builder()
                .versionId("1")
                .name("Version 1")
                .dimensions(List.of(Dimension.builder()
                                .dimensionId("1")
                                .name("D 1")
                                .levels(List.of(Level.builder()
                                        .levelId("1")
                                        .value(1)
                                        .questions(List.of())
                                        .build()))
                        .build()))
                .build();
    }




    /*

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
