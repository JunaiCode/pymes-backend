package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.ActionPlanDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/actionPlan")
public interface ActionPlanAPI {
    @GetMapping("/getActualActionPlan")
    List<ActionPlanOutDTO> getActualActionPlan(@RequestBody String id);

    @PostMapping("/add")
    ActionPlan createActionPlan(ActionPlanDTO actionPlanDTO, String evaluationId);
}
