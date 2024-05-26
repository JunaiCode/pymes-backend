package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.dto.out.DimensionAPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;

import java.util.List;

public interface ActionPlanConstructor {

    ActionPlanOutDTO constructActionPlan(ActionPlan actionPlan);
}
