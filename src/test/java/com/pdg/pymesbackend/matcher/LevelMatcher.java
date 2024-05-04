package com.pdg.pymesbackend.matcher;


import com.pdg.pymesbackend.model.Level;
import org.mockito.ArgumentMatcher;

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
                level.getScoreMin() == leftLevel.getScoreMin() &&
                level.getScoreMax() == leftLevel.getScoreMax();
    }
}
