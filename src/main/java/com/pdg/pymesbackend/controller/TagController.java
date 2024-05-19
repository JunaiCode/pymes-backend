package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.TagAPI;
import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.model.Tag;
import com.pdg.pymesbackend.service.modules.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class TagController implements TagAPI {

    private TagService tagService;
    @Override
    public Tag save(TagDTO tag) {
        return tagService.save(tag);
    }

    @Override
    public Tag update(String id, TagDTO tag) {
        return tagService.update(id, tag);
    }

    @Override
    public List<Tag> dimensionTags(String dimensionId) {
        return tagService.dimensionTags(dimensionId);
    }

    @Override
    public TagDTO getTag(String tagId) {
        return tagService.getTag(tagId);
    }
}
