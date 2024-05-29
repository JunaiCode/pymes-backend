package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.LevelMapper;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.model.Question;
import com.pdg.pymesbackend.repository.LevelRepository;
import com.pdg.pymesbackend.service.modules.DimensionService;
import com.pdg.pymesbackend.service.modules.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LevelServiceImpl implements LevelService {

    private LevelRepository levelRepository;
    private DimensionService dimensionService;
    @Lazy
    private LevelMapper levelMapper;

    @Override
    public Level save(LevelDTO level, String dimensionId) {
        Level newLevel = levelMapper.fromLevelDTO(level);
        newLevel.setQuestions(List.of());
        checkIfLevelExists(newLevel, dimensionId);
        newLevel = levelRepository.save(newLevel);
        dimensionService.addLevelToDimension(newLevel, dimensionId);
        return newLevel;
    }

    @Override
    public List<Level> getLevelsInDimension(String dimensionId) {
        Dimension dim = dimensionService.get(dimensionId);
        return dim.getLevels();
    }

    private void checkIfLevelExists(Level newLevel, String dimensionId) {
        List<Level> levels =  dimensionService.get(dimensionId).getLevels();
        for (Level level : levels) {
            if (level.getName().equals(newLevel.getName()) || Objects.equals(level.getValue(), newLevel.getValue())) {
                throw new PymeException(PymeExceptionType.LEVEL_ALREADY_EXISTS);
            }
        }
    }

    @Override
    public void updateQuestions(String levelId, List<String> questionsId) {
        Level level = getLevel(levelId);
        level.setQuestions(questionsId);
        levelRepository.save(level);
    }

    @Override
    public Level getLevel(String levelId) {
        return levelRepository.findById(levelId).orElseThrow(() -> new PymeException(PymeExceptionType.LEVEL_NOT_FOUND));
    }
}
