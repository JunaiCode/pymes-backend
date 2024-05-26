package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;

import java.util.List;

public interface ActionPlanService {

    List<ActionPlanOutDTO> getActualActionPlanByCompanyId(String companyId);

    ActionPlan findById(String id);

    ActionPlan getAll();

    ActionPlan save(String evaluationId);
}
