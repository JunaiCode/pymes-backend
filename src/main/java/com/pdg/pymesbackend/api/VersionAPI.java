package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.model.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/version")
public interface VersionAPI {

    @GetMapping("/get/{id}")
    Version get (@PathVariable String id);
}
