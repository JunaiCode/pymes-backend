package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.ActionPlanDTO;
import com.pdg.pymesbackend.mapper.ActionPlanMapper;
import com.pdg.pymesbackend.model.ActionPlan;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.repository.ActionPlanRepository;
import com.pdg.pymesbackend.repository.CompanyRepository;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.service.modules.ActionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActionPlanServiceImpl implements ActionPlanService {

    private CompanyRepository companyRepository;
    private EvaluationRepository evaluationRepository;
    private ActionPlanRepository actionPlanRepository;
    private ActionPlanMapper actionPlanMapper;

    @Override
    public ActionPlan getActualActionPlanByCompanyId(String companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            String[] evaluations = company.get().getEvaluations();
            if (evaluations != null && evaluations.length > 0) {
                // Order Evaluations
                Arrays.sort(evaluations, Comparator.reverseOrder());
                // Choose the most recent evaluation
                String latestEvaluation = evaluations[0];
                Optional<Evaluation> evaluation = evaluationRepository.findById(latestEvaluation);
                if(evaluation.isPresent()){
                    Optional<ActionPlan> actionPlan = actionPlanRepository.findById(evaluation.get().getActionPlanId());
                  if(actionPlan.isPresent()){
                      return actionPlan.get();
                  };
                }
            }
        }
        return null;
    }

    @Override
    public ActionPlan findById(String id) {
        return null;
    }

    @Override
    public ActionPlan getAll() {
        return null;
    }

    @Override
    public ActionPlan save(ActionPlanDTO actionPlanDTO) {
        ActionPlan actionPlan = actionPlanMapper.fromActionPlanDTO(actionPlanDTO);
        return actionPlanRepository.save(actionPlan);
    }
}
