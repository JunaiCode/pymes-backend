package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.ActionPlanAPI;
import com.pdg.pymesbackend.dto.ActionPlanDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import com.pdg.pymesbackend.service.modules.ActionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ActionPlanController implements ActionPlanAPI {

    private final ActionPlanService actionPlanService;

    @Override
    public ActionPlan getActualActionPlan(String id) {
        return actionPlanService.getActualActionPlanByCompanyId(id);
    }

    @Override
    public ActionPlan createActionPlan(ActionPlanDTO actionPlanDTO) {
        return actionPlanService.save(actionPlanDTO);
    }
}
