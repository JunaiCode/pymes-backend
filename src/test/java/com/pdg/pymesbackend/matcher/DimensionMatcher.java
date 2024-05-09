package com.pdg.pymesbackend.matcher;

import com.pdg.pymesbackend.model.Dimension;
import org.mockito.ArgumentMatcher;

public class DimensionMatcher implements ArgumentMatcher<Dimension> {

    /*private final Dimension leftDimension;

    public DimensionMatcher(Dimension dimension) {
        leftDimension = dimension;
    }*/
    @Override
    public boolean matches(Dimension dimension) {
        return false;
                /*
        return dimension.getDimensionId() != null &&
                dimension.getName().equals(leftDimension.getName()) &&
                dimension.getDescription().equals(leftDimension.getDescription());*/
    }
}
