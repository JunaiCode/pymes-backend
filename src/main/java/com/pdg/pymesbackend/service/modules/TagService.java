package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.model.Tag;

import java.util.List;

public interface TagService {

    Tag save (TagDTO tag);
    Tag update (String id, TagDTO tag);
    List<Tag> dimensionTags(String dimensionId);
    Tag getTag(String tagId);
}
