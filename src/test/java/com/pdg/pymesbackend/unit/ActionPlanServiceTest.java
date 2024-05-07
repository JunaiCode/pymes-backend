package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.matcher.DimensionMatcher;
import com.pdg.pymesbackend.model.*;
import com.pdg.pymesbackend.repository.ActionPlanRepository;
import com.pdg.pymesbackend.repository.CompanyRepository;
import com.pdg.pymesbackend.repository.DimensionRepository;
import com.pdg.pymesbackend.repository.EvaluationRepository;
import com.pdg.pymesbackend.service.implementations.ActionPlanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActionPlanServiceTest {
    @InjectMocks
    private ActionPlanServiceImpl service;
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private EvaluationRepository evaluationRepository;
    @Mock
    private ActionPlanRepository actionPlanRepository;

    @Test
    void getActualActionPlan(){
        Evaluation evaluation = Evaluation.builder().evaluationId("1").actionPlanId("1").build();
        String[] evaluations = {"1"};
        Company company = Company.builder().companyId("1").companyType(CompanyType.builder().companyTypeId("1").build()).name("").email("J").address("A").employees(100).evaluations(evaluations).build();
        System.out.println(company);
        System.out.println(service.getActualActionPlanByCompanyId(company.getCompanyId()));
    }
}
