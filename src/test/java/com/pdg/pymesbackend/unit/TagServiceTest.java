package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.mapper.TagMapper;
import com.pdg.pymesbackend.matcher.TagMatcher;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Tag;
import com.pdg.pymesbackend.repository.TagRepository;
import com.pdg.pymesbackend.service.modules.TagService;
import com.pdg.pymesbackend.service.modules.implementations.DimensionServiceImpl;
import com.pdg.pymesbackend.service.modules.implementations.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;

    @Mock
    private DimensionServiceImpl dimensionService;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void testCreateTag() {
        when(tagMapper.fromDTO(createTagDTO())).thenReturn(createTag());
        when(tagRepository.findByNameAndDimensionId("Tag 1", "1")).thenReturn(Optional.empty());
        tagService.save(createTagDTO());
        Tag tag1 = createTag();
        verify(tagRepository, times(1)).save(argThat(new TagMatcher(tag1)));
    }

    @Test
    public void testCreateTagAlreadyExists() {
        when(tagMapper.fromDTO(createTagDTO())).thenReturn(createTag());
        when(tagRepository.findByNameAndDimensionId("Tag 1", "1")).thenReturn(Optional.of(createTag()));
        try {
            tagService.save(createTagDTO());
        } catch (Exception e) {
            verify(tagRepository, times(0)).save(any());
            assertEquals("Tag already exists", e.getMessage());
        }
    }

    @Test
    public void testUpdateTag() {
        when(tagRepository.findById("1")).thenReturn(Optional.of(createTag()));
        when(tagRepository.findByNameAndDimensionId("Tag 1", "1")).thenReturn(Optional.empty());
        tagService.update("1", createTagDTO());
        Tag tag1 = createTag();
        verify(tagRepository, times(1)).save(argThat(new TagMatcher(tag1)));
    }

    @Test
    public void testUpdateTagNotFound() {
        when(tagRepository.findById("1")).thenReturn(Optional.empty());
        try {
            tagService.update("1", createTagDTO());
        } catch (Exception e) {
            verify(tagRepository, times(0)).save(any());
            assertEquals("Tag not found", e.getMessage());
        }
    }

    @Test
    public void testUpdateTagAlreadyExists() {
        when(tagRepository.findById("1")).thenReturn(Optional.of(createTag()));
        when(tagRepository.findByNameAndDimensionId("Tag 1", "1")).thenReturn(Optional.of(createTag()));
        try {
            tagService.update("1", createTagDTO());
        } catch (Exception e) {
            verify(tagRepository, times(0)).save(any());
            assertEquals("Tag already exists", e.getMessage());
        }
    }

    @Test
    void testDimensionTags() {
        when(tagRepository.findByDimensionId("1")).thenReturn(List.of(createTag()));
        when(dimensionService.get("1")).thenReturn(createDimension());
        tagService.dimensionTags("1");
        assertEquals(1, tagService.dimensionTags("1").size());
    }

    @Test
    void testDimensionTagsEmpty() {
        when(tagRepository.findByDimensionId("1")).thenReturn(List.of());
        when(dimensionService.get("1")).thenReturn(createDimension());
        assertEquals(0, tagService.dimensionTags("1").size());
    }

    @Test
    void testDimensionTagsNotFound() {
        when(dimensionService.get("1")).thenThrow(new RuntimeException("Dimension not found"));
        try {
            tagService.dimensionTags("1");
        } catch (Exception e) {
            assertEquals("Dimension not found", e.getMessage());
        }
    }

    @Test
    void testGetTag() {
        when(tagRepository.findById("1")).thenReturn(Optional.of(createTag()));
        Tag result = tagService.getTag("1");
        verify(tagRepository, times(1)).findById("1");
        assertEquals(result.getName(), createTag().getName());
    }
    @Test
    void testGetTagNotFound() {
        when(tagRepository.findById("1")).thenReturn(Optional.empty());
        try {
            tagService.getTag("1");
        } catch (Exception e) {
            assertEquals("Tag not found", e.getMessage());
        }
    }

    Tag createTag() {
        return Tag.builder()
                .tagId("1")
                .name("Tag 1")
                .description("Tag 1 description")
                .dimensionId("1")
                .build();
    }

    Dimension createDimension() {
        return Dimension.builder()
                .dimensionId("1")
                .name("Dimension 1")
                .description("Dimension 1 description")
                .build();
    }

    TagDTO createTagDTO() {
        return TagDTO.builder()
                .name("Tag 1")
                .description("Tag 1 description")
                .dimensionId("1")
                .build();
    }
}
