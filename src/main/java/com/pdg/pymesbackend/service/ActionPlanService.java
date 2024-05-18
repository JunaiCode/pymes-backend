package com.pdg.pymesbackend.service;

import com.pdg.pymesbackend.dto.ActionPlanDTO;
import com.pdg.pymesbackend.model.ActionPlan;

import javax.swing.*;

public interface ActionPlanService {

    ActionPlan getActualActionPlanByCompanyId(String companyId);

    ActionPlan findById(String id);

    ActionPlan getAll();

    ActionPlan save(ActionPlanDTO actionPlanDTO);
}
