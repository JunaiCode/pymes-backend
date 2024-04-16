package com.pdg.pymesbackend.api;


import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.model.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/model")
public interface ModelAPI {

    @PostMapping("/add")
    Model createModel(@RequestBody @Valid ModelDTO model);

}
