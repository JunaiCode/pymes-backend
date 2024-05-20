package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.VersionAPI;
import com.pdg.pymesbackend.dto.DimensionQuestionInDTO;
import com.pdg.pymesbackend.dto.out.DimensionQuestionOutDTO;
import com.pdg.pymesbackend.model.Version;
import com.pdg.pymesbackend.service.modules.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class VersionController implements VersionAPI {
    private VersionService versionService;

    @Override
    public Version get(String id) {
        return versionService.get(id);
    }

    @Override
    public List<DimensionQuestionOutDTO> getQuestionsFirstLevel(String id, String companyTypeId) {
        return versionService.getFirstQuestions(id, companyTypeId);
    }

    @Override
    public List<DimensionQuestionOutDTO> getQuestionsByLevel(DimensionQuestionInDTO dimensionQuestionInDTO) {
        return versionService.getDimensionLevelQuestions(dimensionQuestionInDTO);
    }
}
