package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.model.Dimension;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/dimension")
public interface DimensionAPI {

    @PostMapping("/add/{versionId}")
    Dimension createDimension (@RequestBody @Valid DimensionDTO dimension,@PathVariable String versionId);

    @GetMapping("/get/{id}")
    Dimension getDimension (@PathVariable String id);

    @GetMapping("/get/all")
    List<Dimension> getAllDimensions ();



    @PutMapping("/update/{id}")
    Dimension updateDimension (@RequestBody @Valid DimensionDTO dimension, @PathVariable String id);

    @DeleteMapping("/delete/{id}")
    void deleteDimension (@PathVariable String id);
}
