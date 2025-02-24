package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Level;

import java.util.List;

public interface LevelService {

    Level save(LevelDTO level, String dimensionId);

    List<Level> getLevelsInDimension(String dimensionId);

    void updateQuestions(String levelId, List<String> questionsId);

    Level addQuestionToLevel(String levelId, String questionId);

    Level getLevel(String levelId);


}
