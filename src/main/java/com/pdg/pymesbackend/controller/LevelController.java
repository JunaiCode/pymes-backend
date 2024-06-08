package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.LevelAPI;
import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.service.modules.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class LevelController implements LevelAPI {
    private final LevelService levelService;
    @Override
    public Level createLevel(LevelDTO level,String dimensionId) {
        return levelService.save(level,dimensionId);
    }

    @Override
    public List<Level> getLevelsInDimension(String dimensionId) {
        return levelService.getLevelsInDimension(dimensionId);
    }
}
