package com.pdg.pymesbackend.service;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Level;

public interface LevelService {

    Level save(LevelDTO level);
}
