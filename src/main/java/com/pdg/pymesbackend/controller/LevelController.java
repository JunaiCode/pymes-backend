package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.LevelAPI;
import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.service.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LevelController implements LevelAPI {
    private final LevelService levelService;
    @Override
    public Level createLevel(LevelDTO level) {
        return levelService.save(level);
    }
}
