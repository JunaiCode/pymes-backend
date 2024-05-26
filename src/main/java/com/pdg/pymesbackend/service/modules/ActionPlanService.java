package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.ActionPlanDateDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;

public interface ActionPlanService {

    ActionPlanOutDTO getActualActionPlanByCompanyId(String companyId);

    ActionPlan findById(String id);

    ActionPlan getAll();

    ActionPlan save(String evaluationId);

    void updateEndDate(ActionPlanDateDTO date, String actionPlanId);

    void updateStepTrack(String actionPlanId, String recommendationActionPlanId, boolean completed);


}
