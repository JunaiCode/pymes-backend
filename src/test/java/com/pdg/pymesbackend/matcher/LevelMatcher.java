package com.pdg.pymesbackend.matcher;


import com.pdg.pymesbackend.model.Level;
import org.mockito.ArgumentMatcher;

import java.util.Arrays;
import java.util.List;

public class LevelMatcher implements ArgumentMatcher<Level> {

    private final Level leftLevel;

    public LevelMatcher(Level level) {
        leftLevel = level;
    }
    @Override
    public boolean matches(Level level) {
        return level.getLevelId() != null &&
                level.getName().equals(leftLevel.getName()) &&
                level.getDescription().equals(leftLevel.getDescription()) &&
                level.getValue().equals(leftLevel.getValue())&&
                compareStringLists(level.getQuestions(), leftLevel.getQuestions());
    }

    private boolean compareStringLists(List<String> list1, List<String> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        return list1.size() == list2.size() && list1.containsAll(list2) && list2.containsAll(list1);
    }
}
