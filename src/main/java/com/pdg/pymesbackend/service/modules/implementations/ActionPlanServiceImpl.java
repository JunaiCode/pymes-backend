package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.ActionPlanDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.ActionPlanMapper;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.ActionPlanRepository;
import com.pdg.pymesbackend.service.modules.ActionPlanService;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ActionPlanServiceImpl implements ActionPlanService {

    private ActionPlanRepository actionPlanRepository;
    private ActionPlanMapper actionPlanMapper;
    private EvaluationService evaluationService;
    private CompanyServiceImpl companyService;
    private ActionPlanConstructorImpl actionPlanConstructor;


    @Override
    public List<ActionPlanOutDTO> getActualActionPlanByCompanyId(String companyId) {
        List<String> evaluations = companyService.getCompanyById(companyId).getEvaluations();

        if(evaluations.isEmpty()){
            return new ArrayList<>();
        }else {
            String evaluationId = evaluations.get(evaluations.size()-1);
            Evaluation evaluation = evaluationService.getEvaluationById(evaluationId);
            String actionPlanId = evaluation.getActionPlanId();
            return actionPlanConstructor.constructActionPlan(findById(actionPlanId));
        }
    }


    @Override
    public ActionPlan findById(String id) {
        return actionPlanRepository.findById(id).orElseThrow(() -> new PymeException(PymeExceptionType.ACTION_PLAN_NOT_FOUND));

    }

    @Override
    public ActionPlan getAll() {
        return null;
    }

    @Override
    public ActionPlan save(ActionPlanDTO actionPlanDTO, String evaluationId) {
        String actionPlanId = evaluationService.getEvaluationById(evaluationId).getActionPlanId();
        ActionPlan actionPlan = findById(actionPlanId);
        actionPlan.setStart(actionPlanDTO.getStartDate());
        return actionPlanRepository.save(actionPlan);
    }

    public ActionPlan save(ActionPlan actionPlan) {
        return actionPlanRepository.save(actionPlan);
    }
}
