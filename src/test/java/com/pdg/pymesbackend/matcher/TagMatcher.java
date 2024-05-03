package com.pdg.pymesbackend.matcher;

import com.pdg.pymesbackend.model.Tag;
import org.mockito.ArgumentMatcher;

public class TagMatcher implements ArgumentMatcher<Tag> {

    private Tag left;

    public TagMatcher(Tag left) {
        this.left = left;
    }
    @Override
    public boolean matches(Tag tag) {
        return left.getTagId()!=null &&
                left.getName().equals(tag.getName()) &&
                left.getDescription().equals(tag.getDescription());
    }
}
