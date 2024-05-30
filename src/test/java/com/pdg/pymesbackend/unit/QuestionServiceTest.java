package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.OptionDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.dto.StepDTO;
import com.pdg.pymesbackend.dto.out.QuestionOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.OptionMapper;
import com.pdg.pymesbackend.mapper.QuestionMapper;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.QuestionRepository;
import com.pdg.pymesbackend.service.modules.implementations.LevelServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.QuestionServiceImpl;
import com.pdg.pymesbackend.service.validator.DimensionValidator;
import com.pdg.pymesbackend.service.validator.implementations.VersionValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private OptionMapper optionMapper;
    @Mock
    private DimensionValidator dimensionValidator;
    @Mock
    private VersionValidatorImpl versionValidator;
    @Mock
    private LevelServiceImpl levelService;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    void testCreateQuestion() {
        QuestionDTO questionDTO = createQuestionDTO();
        Question question = createQuestion();
        when(questionMapper.fromDTO(questionDTO)).thenReturn(question);
        when(questionRepository.save(question)).thenAnswer(invocation -> invocation.getArgument(0));
        ArgumentCaptor<Question> captor = ArgumentCaptor.forClass(Question.class);
        questionService.createQuestion(questionDTO);
        verify(questionRepository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getOptions().size(), 2);
        assertEquals(captor.getValue().getRecommendation().getSteps().size(), 2);
    }

    @Test
    void testCreateQuestionVersionNotExists(){
        QuestionDTO questionDTO = createQuestionDTO();
        when(versionValidator.validateVersion(questionDTO.getVersionId())).thenThrow(new PymeException(PymeExceptionType.VERSION_NOT_FOUND));
        try {
            questionService.createQuestion(questionDTO);
        } catch (Exception e) {
            verify(questionRepository, never()).save(any());
            assertEquals(e.getMessage(), "Version not found");
        }
    }

    @Test
    void testCreateQuestionDimensionNotExists(){
        QuestionDTO questionDTO = createQuestionDTO();
        when(dimensionValidator.validateDimensionExists(questionDTO.getDimensionId())).thenThrow(new PymeException(PymeExceptionType.DIMENSION_NOT_FOUND));
        try {
            questionService.createQuestion(questionDTO);
        } catch (Exception e) {
            verify(questionRepository, never()).save(any());
            assertEquals(e.getMessage(), "Dimension not found");
        }
    }

    @Test
    void testGetQuestion(){
        Question question = createQuestion();
        when(questionRepository.findById("1")).thenReturn(java.util.Optional.of(question));
        assertEquals(question, questionService.getQuestion("1"));
    }

    @Test
    void testGetQuestionNotFound(){
        when(questionRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            questionService.getQuestion("1");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Question not found");
        }
    }

    @Test
    void testGetQuestionsByTag(){
        Question question = createQuestion();
        when(questionRepository.findByTagId("1")).thenReturn(List.of(question));
        assertEquals(List.of(question), questionService.getQuestionsByTag("1"));
    }

    @Test
    void testGetQuestionsByLevel(){
        when(levelService.getLevel("1")).thenReturn(createLevel());
        when(questionRepository.findAllById(List.of("1"))).thenReturn(List.of(createQuestion()));
        assertEquals(List.of(createQuestion()), questionService.getQuestionsByLevel("1"));
    }

    @Test
    void testDeleteQuestion(){
        Question question = createQuestion();
        when(questionRepository.findById("1")).thenReturn(java.util.Optional.of(question));
        questionService.deleteQuestion("1");
        verify(questionRepository, times(1)).delete(question);
    }

    @Test
    void testDeleteQuestionNotFound(){
        when(questionRepository.findById("1")).thenReturn(java.util.Optional.empty());
        try {
            questionService.deleteQuestion("1");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Question not found");
        }
    }

    @Test
    void testFilterQuestionsByCompanyType(){
        Question question = createQuestion();
        when(questionRepository.findQuestionsByCompanyType(List.of("1"), "1")).thenReturn(List.of(question));
        assertEquals(List.of(question), questionService.filterQuestionsByCompanyType(List.of("1"), "1"));
    }

    @Test
    void testFilterQuestionsByCompanyTypeNotFound(){
        when(questionRepository.findQuestionsByCompanyType(List.of("1"), "2")).thenReturn(List.of());
        assertEquals(List.of(), questionService.filterQuestionsByCompanyType(List.of("1"), "2"));
    }

    @Test
    void testMapQuestionToOutDTO(){
        Question question = createQuestion();
        QuestionOutDTO questionOut = questionService.mapQuestionToOutDTO(question);
        assertEquals(question.getQuestion(), questionOut.getQuestion());
        assertEquals(question.getOptions(), questionOut.getOptions());
        assertEquals(question.getScorePositive(), questionOut.getMaxScore());
        assertFalse(questionOut.isMarked());
        assertNull(questionOut.getAnswer());
    }

    @Test
    void testMapAnswerToQuestionOutDTO(){
        Question question = createQuestion();
        EvaluationResult evaluationResult = EvaluationResult.builder()
                .questionId("1")
                .optionId("1")
                .marked(true)
                .build();
        when(questionRepository.findById("1")).thenReturn(java.util.Optional.of(question));
        QuestionOutDTO questionOut = questionService.mapAnswerToQuestionOutDTO(evaluationResult);
        assertEquals(question.getQuestion(), questionOut.getQuestion());
        assertEquals(question.getOptions(), questionOut.getOptions());
        assertEquals(question.getScorePositive(), questionOut.getMaxScore());
        assertTrue(questionOut.isMarked());
        assertNotNull(questionOut.getAnswer());
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
                .question("Question 1")
                .weight(1.0)
                .scorePositive(1)
                .options(List.of(
                        Option.builder()
                                .optionId("1")
                                .description("Option 1")
                                .value(1)
                                .build(),
                        Option.builder()
                                .optionId("2")
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

    Level createLevel() {
        return Level.builder()
                .levelId("1")
                .value(1)
                .questions(List.of("1"))
                .name("Level 1")
                .description("Level 1 description")
                .build();
    }
}
