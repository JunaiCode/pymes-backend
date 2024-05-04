package com.pdg.pymesbackend.matcher;

import com.pdg.pymesbackend.model.Version;
import org.mockito.ArgumentMatcher;

public class VersionMatcher implements ArgumentMatcher<Version> {

    private final Version left;

    public VersionMatcher(Version left) {
        this.left = left;
    }
    @Override
    public boolean matches(Version version) {
        return left.getVersionId() != null &&
                left.getName().equals(version.getName()) &&
                left.getLevels().equals(version.getLevels()) &&
                left.getDimensions().equals(version.getDimensions());

    }
}
