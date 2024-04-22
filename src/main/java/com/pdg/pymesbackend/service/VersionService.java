package com.pdg.pymesbackend.service;


import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.model.Version;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

public interface VersionService {

    Version save(VersionDTO version);
}
