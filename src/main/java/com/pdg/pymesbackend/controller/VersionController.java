package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.VersionAPI;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.service.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class VersionController implements VersionAPI {
    private VersionService versionService;

    @Override
    public Version get(String id) {
        return versionService.get(id);
    }
}
