package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.*;
import com.pdg.pymesbackend.dto.out.DimensionQuestionOutDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.VersionMapper;
import com.pdg.pymesbackend.matcher.VersionMatcher;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.repository.VersionRepository;
import com.pdg.pymesbackend.service.modules.implementations.QuestionServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.VersionServiceImpl;
import com.pdg.pymesbackend.service.validator.implementations.VersionValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VersionServiceTest {

    @Mock
    private VersionRepository versionRepository;
    @Mock
    private VersionMapper versionMapper;
    @Mock
    private QuestionServiceImpl questionService;

    @Mock
    private DimensionRepository dimensionRepository;

    @Mock
    private VersionValidatorImpl versionValidator;
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

    @Test
    void testAddDimension() {
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        when(versionRepository.save(any(Version.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ArgumentCaptor<Version> versionCaptor = ArgumentCaptor.forClass(Version.class);
        versionService.addDimension("1", Dimension.builder().build());
        verify(versionRepository, times(1)).save(versionCaptor.capture());
        assertEquals(2, versionCaptor.getValue().getDimensions().size());
    }

    @Test
    void testAddDimensionWithInvalidVersion() {
        when(versionValidator.validateVersion("1")).thenThrow(new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
        try {
            versionService.addDimension("1", Dimension.builder().build());
        } catch (Exception e) {
            verify(versionRepository, times(0)).save(any());
            assertEquals("Version not found", e.getMessage());
        }
    }

    @Test
    void testFindDimensionInVersionByNameTrue() {
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        try {
            versionService.findDimensionInVersionByName("1", "D 1");
        } catch (Exception e) {
            assertEquals("Dimension already exists", e.getMessage());
        }
    }

    @Test
    void testFindDimensionInVersionByNameFalse() {
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        assertDoesNotThrow(() -> {
            versionService.findDimensionInVersionByName("1", "D 2");
        });
    }

    @Test
    void testFindVersionByDimensionId() {
        when(versionRepository.findVersionByDimensionId("1")).thenReturn(java.util.Optional.of(createVersion()));
        assertDoesNotThrow(() -> {
            versionService.findVersionByDimensionId("1");
        });
    }

    @Test
    void testFindVersionByDimensionIdNotFound() {
        when(versionRepository.findVersionByDimensionId("1")).thenReturn(java.util.Optional.empty());
        try {
            versionService.findVersionByDimensionId("1");
        } catch (Exception e) {
            assertEquals("Version not found", e.getMessage());
        }
    }

    @Test
    void testUpdateVersion() {
        when(versionRepository.save(createVersion2())).thenReturn(createVersion2());
        Version version = createVersion2();
        versionService.updateWithVersion(version);
        verify(versionRepository, times(1)).save(argThat(new VersionMatcher(version)));
    }

    @Test
    void testGetActualVersion(){
        when(versionRepository.findActualVersion()).thenReturn(java.util.Optional.of(createVersion()));
        String versionId = versionService.getActualVersion();
        assertEquals("1", versionId);
    }

    @Test
    void testGetActualVersionNotFound(){
        when(versionRepository.findActualVersion()).thenReturn(java.util.Optional.empty());
        try {
            versionService.getActualVersion();
        } catch (Exception e) {
            assertEquals("Version not found", e.getMessage());
        }
    }

    @Test
    void testAddQuestion(){
        QuestionDTO questionDTO = createQuestionDTO();
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        when(questionService.createQuestion(questionDTO)).thenReturn(createQuestion());
        when(versionRepository.save(any(Version.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ArgumentCaptor<Version> versionCaptor = ArgumentCaptor.forClass(Version.class);
        Version result = versionService.addQuestion(questionDTO);
        verify(versionRepository, times(1)).save(versionCaptor.capture());
        assertEquals(4, result.getDimensions().get(0).getLevels().get(1).getQuestions().size());
        verify(dimensionRepository, times(1)).save(any(Dimension.class));
    }

    @Test
    void testAddQuestionDimensionNotFound(){
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        try {
            versionService.addQuestion(QuestionDTO.builder().versionId("1").dimensionId("2").build());
        } catch (Exception e) {
            verify(versionRepository, times(0)).save(any());
            assertEquals("Dimension not found", e.getMessage());
        }
    }

    @Test
    void testAddQuestionLevelNotFound(){
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        try {
            versionService.addQuestion(QuestionDTO.builder().versionId("1").dimensionId("1").levelId("3").build());
        } catch (Exception e) {
            verify(versionRepository, times(0)).save(any());
            assertEquals("Level not found", e.getMessage());
        }
    }

    @Test
    void testGetDimensionLevelQuestions(){
        DimensionQuestionInDTO data = DimensionQuestionInDTO.builder()
                .companyTypeId("1")
                .dimensionId("1")
                .levelId("2")
                .versionId("1")
                .build();
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        when(questionService.filterQuestionsByCompanyType(List.of(), "1")).thenReturn(List.of());
        List<DimensionQuestionOutDTO> result = versionService.getDimensionLevelQuestions(data);
        assertEquals(0, result.get(0).getQuestions().size());
    }

    @Test
    void testGetFirstQuestions(){
        when(versionValidator.validateVersion("1")).thenReturn(createVersion());
        Question question1 = Question.builder().questionId("1").build();
        Question question2 = Question.builder().questionId("2").build();
        Question question3 = Question.builder().questionId("3").build();
        when(questionService.filterQuestionsByCompanyType(List.of("1", "2", "3"), "1"))
                .thenReturn(List.of(
                        question1,
                        question2,
                        question3
                ));
        when(questionService.mapQuestionToOutDTO(any(Question.class))).thenReturn(QuestionOutDTO.builder().question("1").build());
        List<DimensionQuestionOutDTO> result = versionService.getFirstQuestions("1", "1");
        assertEquals(3, result.get(0).getQuestions().size());
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
                                        .questions(List.of("1", "2", "3"))
                                        .build(), Level.builder()
                                        .levelId("2")
                                        .value(2)
                                        .questions(List.of()).build()))
                        .build()))
                .build();
    }

    Version createVersion2() {
        return Version.builder()
                .versionId("2")
                .name("Version 2")
                .dimensions(List.of(Dimension.builder()
                        .dimensionId("1")
                        .name("D1")
                        .levels(List.of(Level.builder()
                                .levelId("1")
                                .value(1)
                                .questions(List.of("1", "2", "3"))
                                .build()))
                        .build()))
                .build();
    }

    VersionDTO createVersionDTO() {
        return VersionDTO.builder()
                .name("Version 1")
                .dimensions(List.of(DimensionDTO.builder()
                        .name("D 1")
                        .levels(List.of(LevelDTO.builder()
                                .value(1)
                                .questions(List.of("1", "2"))
                                .build()))
                        .build()))
                .build();

    }

    QuestionDTO createQuestionDTO() {
        return QuestionDTO.builder()
                .versionId("1")
                .dimensionId("1")
                .options(List.of(
                        OptionDTO.builder()
                        .description("Option 1")
                        .value(1)
                        .build(),
                        OptionDTO.builder()
                        .description("Option 2")
                        .value(2)
                        .build()))
                .recommendation(RecommendationDTO.builder()
                        .description("Recommendation 1")
                        .steps(List.of(
                                StepDTO.builder().description("Step 1").build(),
                                StepDTO.builder().description("Step 2").build()
                        ))
                        .build())
                .levelId("1")
                .tagId("1")
                .companyTypeId(1)
                .weight(1.0)
                .scorePositive(1)
                .question("Question 1")
                .build();
    }

    Question createQuestion(){
        return Question.builder()
                .questionId("1")
                .question("Question 1")
                .weight(1.0)
                .scorePositive(1)
                .options(List.of(
                        Option.builder()
                        .description("Option 1")
                        .value(1)
                        .build(),
                        Option.builder()
                        .description("Option 2")
                        .value(2)
                        .build()))
                .recommendation(Recommendation.builder()
                        .description("Recommendation 1")
                        .steps(List.of(
                                Step.builder().description("Step 1").build(),
                                Step.builder().description("Step 2").build()
                        ))
                        .build())
                .dimensionId("1")
                .tagId("1")
                .companyTypeId("1")
                .build();
    }

}
