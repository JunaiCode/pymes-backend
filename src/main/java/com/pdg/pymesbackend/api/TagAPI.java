package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.TagDTO;
import com.pdg.pymesbackend.model.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/tag")
public interface TagAPI {

    @PostMapping("/add")
    Tag save (@RequestBody TagDTO tag);
    @PutMapping("/update/{id}")
    Tag update (@PathVariable String id, @Valid @RequestBody TagDTO tag);

    @GetMapping("/get/dimension/{dimensionId}")
    List<Tag> dimensionTags(@PathVariable String dimensionId);

    @GetMapping("/get/{tagId}")
    Tag getTag( @PathVariable String tagId);
}
