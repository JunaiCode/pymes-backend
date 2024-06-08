package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;


public interface ActionPlanConstructor {

    ActionPlanOutDTO constructActionPlan(ActionPlan actionPlan);
}
