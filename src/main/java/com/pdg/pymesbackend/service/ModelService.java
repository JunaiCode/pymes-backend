package com.pdg.pymesbackend.service;

import com.pdg.pymesbackend.dto.ModelDTO;
import com.pdg.pymesbackend.model.Model;

public interface ModelService {

    Model save (ModelDTO model);
}
