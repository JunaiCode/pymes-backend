package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.LevelDTO;
import com.pdg.pymesbackend.model.Level;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/level")
public interface LevelAPI {

    @PostMapping("/add/{dimensionId}")
    Level createLevel(@RequestBody @Valid LevelDTO level, @PathVariable String dimensionId);

    @GetMapping("/dimension/{dimensionId}")
    List<Level> getLevelsInDimension(@PathVariable String dimensionId);
}
