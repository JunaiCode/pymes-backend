package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.DateDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/actionPlan")
public interface ActionPlanAPI {

    @PostMapping("/add/evaluation/{evaluationId}")
    ActionPlan createActionPlan(@PathVariable String evaluationId);

    @PutMapping("/updateEnd/{actionPlanId}")
    void updateEndDate(@RequestBody DateDTO date, @PathVariable String actionPlanId);

    @PutMapping("/updateStepTrack/{actionPlanId}/{recommendationActionPlanId}/{completed}")
    void updateStepTrack(@PathVariable String actionPlanId, @PathVariable String recommendationActionPlanId, @PathVariable boolean completed);
}
