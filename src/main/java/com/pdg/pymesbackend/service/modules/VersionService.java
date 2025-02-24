package com.pdg.pymesbackend.service.modules;


import com.pdg.pymesbackend.dto.DimensionQuestionInDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.out.DimensionQuestionOutDTO;
import com.pdg.pymesbackend.dto.VersionDTO;
import com.pdg.pymesbackend.model.Dimension;
import com.pdg.pymesbackend.model.Version;

import java.util.List;

public interface VersionService {

    Version save(VersionDTO version);

    void addDimension(String versionId, Dimension newDimension);

    Version get(String id);

    void findDimensionInVersionByName(String versionId, String dimensionName);

    Version findVersionByDimensionId(String dimensionId);

    Version updateWithVersion(Version version);

    Version addQuestion(QuestionDTO questionDTO);

    List<DimensionQuestionOutDTO> getFirstQuestions(String versionId, String companyTypeId);

    List<DimensionQuestionOutDTO> getDimensionLevelQuestions(DimensionQuestionInDTO dimensionQuestionInDTO);

    String getActualVersion();
}
