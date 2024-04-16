package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.model.Dimension;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/dimension")
public interface DimensionAPI {

    @PostMapping("/add")
    Dimension createDimension (@RequestBody @Valid DimensionDTO dimension);
}
