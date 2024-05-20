package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.DimensionQuestionInDTO;
import com.pdg.pymesbackend.dto.out.DimensionQuestionOutDTO;
import com.pdg.pymesbackend.model.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/version")
public interface VersionAPI {

    @GetMapping("/get/{id}")
    Version get (@PathVariable String id);

    @GetMapping("/get/{id}/questions/{companyTypeId}/first-level")
    List<DimensionQuestionOutDTO> getQuestionsFirstLevel(@PathVariable String id, @PathVariable String companyTypeId);

    @GetMapping("/get/questions/company-type/level")
    List<DimensionQuestionOutDTO> getQuestionsByLevel(@Valid DimensionQuestionInDTO dimensionQuestionInDTO);
}
