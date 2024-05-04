package com.pdg.pymesbackend.service;


import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.model.Version;

public interface VersionService {

    Version save(VersionDTO version);
}
