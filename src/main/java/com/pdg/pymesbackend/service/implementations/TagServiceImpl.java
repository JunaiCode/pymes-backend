package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.mapper.TagMapper;
import com.pdg.pymesbackend.model.Tag;
import com.pdg.pymesbackend.repository.TagRepository;
import com.pdg.pymesbackend.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private TagMapper tagMapper;
    private TagRepository tagRepository;

    @Override
    public Tag save(TagDTO tag) {
        Tag newTag = tagMapper.fromDTO(tag);
        findByNameAndDimensionId(newTag.getName(), newTag.getDimensionId());
        return tagRepository.save(newTag);
    }


    @Override
    public Tag update(String id, TagDTO tag) {
        Tag oldTag = tagRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tag not found")
        );
        findByNameAndDimensionId(tag.getName(), tag.getDimensionId());

        //Add validation to check id the dimension exists

        oldTag.setName(tag.getName());
        oldTag.setDescription(tag.getDescription());
        oldTag.setDimensionId(tag.getDimensionId());

        return tagRepository.save(oldTag);
    }

    private void findByNameAndDimensionId(String name, String dimensionId) {
        tagRepository.findByNameAndDimensionId(name, dimensionId).orElseThrow(
                () -> new RuntimeException("Tag not found")
        );
    }

    @Override
    public List<Tag> dimensionTags(String dimensionId) {
        return tagRepository.findByDimensionId(dimensionId);
    }
}
