package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.dto.out.CompanyOutDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.error.PymeException;
import com.pdg.pymesbackend.error.PymeExceptionType;
import com.pdg.pymesbackend.mapper.CompanyMapper;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.CompanyType;
import com.pdg.pymesbackend.model.DimensionResult;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.repository.CompanyRepository;
import com.pdg.pymesbackend.service.modules.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyMapper companyMapper;
    private CompanyRepository companyRepository;
    private EvaluationServiceImpl evaluationService;
    private ActionPlanServiceImpl actionPlanService;
    //private final PasswordEncoder encoder;


    @Override
    public Company save(CompanyDTO companyDTO) {
        companyRepository.findByLegalRepEmail(companyDTO.getEmail()).ifPresent(company -> {
            throw new PymeException(PymeExceptionType.COMPANY_ALREADY_EXISTS);
        });
        Company company = companyMapper.fromCompanyDTO(companyDTO);
        company.setCompanyId(UUID.randomUUID().toString());
        company.setEvaluations(List.of());
        return companyRepository.save(company);
    }

    @Override
    public Company save(RegisterDTO registerDTO) {
        companyRepository.findByLegalRepEmail(registerDTO.getLegalRepEmail()).ifPresent(company -> {
            throw new PymeException(PymeExceptionType.COMPANY_ALREADY_EXISTS);
        });
        Company company = companyMapper.fromRegisterDTO(registerDTO);
        company.setCompanyId(UUID.randomUUID().toString());
        company.setEvaluations(List.of());
        company.setCreationDate(LocalDateTime.now());
        company.setCompanyType(companyTypeConstructor(registerDTO.getType()));

        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(String companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new PymeException(PymeExceptionType.COMPANY_NOT_FOUND));
    }

    @Override
    public OnGoingEvaluationOutDTO checkUncompletedEvaluation(String companyId) {
        Company company = getCompanyById(companyId);
        return evaluationService.checkUncompletedEvaluation(company);
    }

    @Override
    public ActionPlanOutDTO getActualActionPlan(String companyId) {
        Company company = getCompanyById(companyId);
        return actionPlanService.getActualActionPlanByCompanyId(company);
    }

    @Override
    public Evaluation startEvaluation(String companyId) {
        Company company = getCompanyById(companyId);
        Evaluation newEvaluation = evaluationService.save(companyId);
        company.getEvaluations().add(newEvaluation.getEvaluationId());
        companyRepository.save(company);
        return newEvaluation;
    }

    @Override
    public CompanyOutDTO getCompanyOut(String companyId) {
        Company company = getCompanyById(companyId);

        return CompanyOutDTO.builder()
                .companyType(company.getCompanyType())
                .name(company.getName())
                .employees(company.getEmployees())
                .economicSectorId(company.getEconomicSectorId())
                .dimensionResults(getLastEvaluationResults(company))
                .build();

    }

    private List<DimensionResult> getLastEvaluationResults(Company company){
        if(company.getEvaluations().isEmpty()){
            return null;
        }else {
            String lastEvaluationId = company.getEvaluations().get(company.getEvaluations().size()-1);
            Evaluation lastEvaluation = evaluationService.getEvaluationById(lastEvaluationId);
            return lastEvaluation.getDimensionResults();
        }
    }

    @Override
    public Company getCompanyByEmail(String email) {
        return companyRepository.findByLegalRepEmail(email).orElseThrow(() -> new PymeException(PymeExceptionType.COMPANY_NOT_FOUND));
    }

    private CompanyType companyTypeConstructor(Integer companyType){
        return switch (companyType) {
            case 1 -> CompanyType.builder()
                    .companyTypeId("MICRO")
                    .name("Micro Empresa")
                    .description("Menos de 10 empleados")
                    .build();
            case 2 -> CompanyType.builder()
                    .companyTypeId("PEQUENA")
                    .name("Pequeña Empresa")
                    .description("Entre 10 y 50 empleados")
                    .build();
            case 3 -> CompanyType.builder()
                    .companyTypeId("MEDIANA")
                    .name("Mediana Empresa")
                    .description("Entre 50 y 200 empleados")
                    .build();
            default -> null;
        };
    }
}
