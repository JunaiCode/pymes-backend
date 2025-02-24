package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.DimensionQuestionInDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.out.DimensionQuestionOutDTO;
import com.pdg.pymesbackend.model.Version;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/version")
public interface VersionAPI {

    @GetMapping("/get/{id}")
    Version get (@PathVariable String id);

    @GetMapping("/get/{id}/questions/{companyTypeId}/first-level")
    List<DimensionQuestionOutDTO> getQuestionsFirstLevel(@PathVariable String id, @PathVariable String companyTypeId);

    @PostMapping("/get/questions/company-type/level")
    List<DimensionQuestionOutDTO> getQuestionsByLevel(@RequestBody @Valid DimensionQuestionInDTO dimensionQuestionInDTO);

    @PostMapping("/add/question")
    Version createQuestion(@RequestBody @Valid QuestionDTO question);
}
