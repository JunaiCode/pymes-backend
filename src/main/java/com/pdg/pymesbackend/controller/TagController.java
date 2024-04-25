package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.TagAPI;
import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.model.Tag;
import com.pdg.pymesbackend.service.TagService;

import java.util.List;

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
}
