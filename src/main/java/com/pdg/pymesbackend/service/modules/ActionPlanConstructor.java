package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;

import java.util.List;

public interface ActionPlanConstructor {

    List<ActionPlanOutDTO> constructActionPlan(ActionPlan actionPlan);
}
