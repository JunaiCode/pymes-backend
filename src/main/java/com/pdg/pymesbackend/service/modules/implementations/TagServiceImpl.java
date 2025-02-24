package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.TagMapper;
import com.pdg.pymesbackend.model.Tag;
import com.pdg.pymesbackend.repository.TagRepository;
import com.pdg.pymesbackend.service.modules.DimensionService;
import com.pdg.pymesbackend.service.modules.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private TagMapper tagMapper;
    private TagRepository tagRepository;
    private DimensionService dimensionService;

    @Override
    public Tag save(TagDTO tag) {
        Tag newTag = tagMapper.fromDTO(tag);
        tagRepository.findByNameAndDimensionId(tag.getName(), tag.getDimensionId()).ifPresent(
                existingTag -> {
                    throw new PymeException(PymeExceptionType.TAG_ALREADY_EXISTS);
                }
        );
        return tagRepository.save(newTag);
    }


    @Override
    public Tag update(String id, TagDTO tag) {
        Tag oldTag = getTag(id);
        findByNameAndDimensionId(tag.getName(), tag.getDimensionId());
        oldTag.setName(tag.getName());
        oldTag.setDescription(tag.getDescription());
        oldTag.setDimensionId(tag.getDimensionId());

        return tagRepository.save(oldTag);
    }

    private void findByNameAndDimensionId(String name, String dimensionId) {
        tagRepository.findByNameAndDimensionId(name, dimensionId).ifPresent(
                existingTag -> {
                    throw new PymeException(PymeExceptionType.TAG_ALREADY_EXISTS);
                }
        );
    }

    @Override
    public List<Tag> dimensionTags(String dimensionId) {
        dimensionService.get(dimensionId);
        return tagRepository.findByDimensionId(dimensionId);
    }

    @Override
    public Tag getTag(String tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new PymeException(PymeExceptionType.TAG_NOT_FOUND)
        );
    }
}
