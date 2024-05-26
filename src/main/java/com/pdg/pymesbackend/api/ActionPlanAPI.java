package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.ActionPlanDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/actionPlan")
public interface ActionPlanAPI {
    @GetMapping("/getActualActionPlan/{companyId}")
    List<ActionPlanOutDTO> getActualActionPlan(@PathVariable String companyId);

    @PostMapping("/add/evaluation/{evaluationId}")
    ActionPlan createActionPlan(@PathVariable String evaluationId);
}
