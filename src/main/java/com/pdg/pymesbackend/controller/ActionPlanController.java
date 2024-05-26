package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.ActionPlanAPI;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import com.pdg.pymesbackend.service.modules.ActionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ActionPlanController implements ActionPlanAPI {

    private final ActionPlanService actionPlanService;

    @Override
    public List<ActionPlanOutDTO> getActualActionPlan(String id) {
        return actionPlanService.getActualActionPlanByCompanyId(id);
    }

    @Override
    public ActionPlan createActionPlan(String evaluationId) {
        return actionPlanService.save(evaluationId);
    }
}
