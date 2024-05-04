package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Level;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/level")
public interface LevelAPI {

    @PostMapping("/add")
    Level createLevel(@RequestBody @Valid LevelDTO level);
}
