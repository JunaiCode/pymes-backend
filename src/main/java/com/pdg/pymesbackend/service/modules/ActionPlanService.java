package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.DateDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.model.ActionPlan;
import com.pdg.pymesbackend.model.Company;

public interface ActionPlanService {

    ActionPlanOutDTO getActualActionPlanByCompanyId(Company company);

    ActionPlan findById(String id);

    ActionPlan getAll();

    ActionPlan save(String evaluationId);

    void updateEndDate(DateDTO date, String actionPlanId);

    void updateStepTrack(String actionPlanId, String recommendationActionPlanId, boolean completed);


}
