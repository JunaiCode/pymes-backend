package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/actionPlan")
public interface ActionPlanAPI {
    @GetMapping("/getActualActionPlan/{companyId}")
    ActionPlanOutDTO getActualActionPlan(@PathVariable String companyId);

    @PostMapping("/add/evaluation/{evaluationId}")
    ActionPlan createActionPlan(@PathVariable String evaluationId);

    @PutMapping("/updateEnd/{actionPlanId}")
    void updateEndDate(@RequestBody String date, @PathVariable String actionPlanId);

    @PutMapping("/updateStepTrack/{actionPlanId}/{recommendationActionPlanId}/{completed}")
    void updateStepTrack(@PathVariable String actionPlanId, @PathVariable String recommendationActionPlanId, @PathVariable boolean completed);
}
