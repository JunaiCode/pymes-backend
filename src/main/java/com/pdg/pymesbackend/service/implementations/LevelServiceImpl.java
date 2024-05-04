package com.pdg.pymesbackend.service.implementations;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.mapper.LevelMapper;
import com.pdg.pymesbackend.model.Level;
import com.pdg.pymesbackend.repository.LevelRepository;
import com.pdg.pymesbackend.service.LevelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LevelServiceImpl implements LevelService {

    private LevelRepository levelRepository;
    private LevelMapper levelMapper;

    @Override
    public Level save(LevelDTO level) {
        Level newLevel = levelMapper.fromLevelDTO(level);
        return levelRepository.save(newLevel);
    }
}
