package com.pdg.pymesbackend.matcher;

import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Level;
import org.mockito.ArgumentMatcher;

import java.util.List;

public class DimensionMatcher implements ArgumentMatcher<Dimension> {

    private final Dimension leftDimension;

    public DimensionMatcher(Dimension dimension) {
        leftDimension = dimension;
    }
    @Override
    public boolean matches(Dimension dimension) {
        return dimension.getDimensionId() != null &&
                dimension.getName().equals(leftDimension.getName()) &&
                dimension.getDescription().equals(leftDimension.getDescription());
    }

    private boolean compareLevels(List<Level> list1, List<Level> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        return list1.size() == list2.size() && list1.containsAll(list2) && list2.containsAll(list1);
    }
}
