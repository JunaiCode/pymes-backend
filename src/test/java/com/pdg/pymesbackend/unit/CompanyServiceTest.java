package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.CompanyInfoDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.ActionPlanOutDTO;
import com.pdg.pymesbackend.dto.out.CompanyOutDTO;
import com.pdg.pymesbackend.dto.out.OnGoingEvaluationOutDTO;
import com.pdg.pymesbackend.mapper.CompanyMapper;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.CompanyType;
import com.pdg.pymesbackend.model.DimensionResult;
import com.pdg.pymesbackend.model.Evaluation;
import com.pdg.pymesbackend.repository.CompanyRepository;
import com.pdg.pymesbackend.service.modules.ActionPlanService;
import com.pdg.pymesbackend.service.modules.EvaluationService;
import com.pdg.pymesbackend.service.modules.implementations.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private EvaluationService evaluationService;
    @Mock
    private ActionPlanService actionPlanService;
    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void testSave() {
        Company company = createCompany();
        RegisterDTO registerDTO = createRegisterDTO();
        when(companyRepository.findByLegalRepEmail(company.getLegalRepEmail())).thenReturn(Optional.empty());
        when(companyMapper.fromRegisterDTO(registerDTO)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        Company savedCompany = companyService.save(registerDTO);
        assertNotNull(savedCompany);
        assertEquals(company.getName(), savedCompany.getName());
        assertEquals(company.getAddress(), savedCompany.getAddress());
        assertEquals(company.getEmployees(), savedCompany.getEmployees());
    }

    @Test
    void testSaveCompanyExists() {
        Company company = createCompany();
        RegisterDTO registerDTO = createRegisterDTO();
        when(companyRepository.findByLegalRepEmail(company.getLegalRepEmail())).thenReturn(Optional.of(company));
        try {
            companyService.save(registerDTO);
        } catch (Exception e) {
            assertEquals("Company already exists", e.getMessage());
        }
    }

    @Test
    void testGetCompany() {
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        Company foundCompany = companyService.getCompanyById(company.getCompanyId());
        assertNotNull(foundCompany);
        assertEquals(company.getName(), foundCompany.getName());
        assertEquals(company.getAddress(), foundCompany.getAddress());
        assertEquals(company.getEmployees(), foundCompany.getEmployees());
    }

    @Test
    void testGetCompanyNotFound(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.empty());
        try {
            companyService.getCompanyById(company.getCompanyId());
        } catch (Exception e) {
            assertEquals("Company not found", e.getMessage());
        }
    }

    @Test
    void testCheckUncompletedEvaluation(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        when(evaluationService.checkUncompletedEvaluation(company)).thenReturn(OnGoingEvaluationOutDTO.builder()
                .evaluationId("1")
                .questions(List.of())
                .build());
        OnGoingEvaluationOutDTO result = companyService.checkUncompletedEvaluation(company.getCompanyId());
        assertNotNull(result);
        assertEquals("1", result.getEvaluationId());

    }

    @Test
    void testCheckUncompletedEvaluationEmpty(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        when(evaluationService.checkUncompletedEvaluation(company)).thenReturn(null);
        OnGoingEvaluationOutDTO result = companyService.checkUncompletedEvaluation(company.getCompanyId());
        assertNull(result);
    }

    @Test
    void testGetActualActionPlan(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        when(actionPlanService.getActualActionPlanByCompanyId(company)).thenReturn(ActionPlanOutDTO.builder().build());
        ActionPlanOutDTO result = companyService.getActualActionPlan(company.getCompanyId());
        assertNotNull(result);
    }

    @Test
    void testGetActualActionPlanEmpty(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        when(actionPlanService.getActualActionPlanByCompanyId(company)).thenReturn(null);
        ActionPlanOutDTO result = companyService.getActualActionPlan(company.getCompanyId());
        assertNull(result);
    }

    @Test
    void testStartEvaluation(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        when(evaluationService.save(company.getCompanyId())).thenReturn(Evaluation.builder().evaluationId("1").completed(false).build());
        ArgumentCaptor<Company> companyCaptor = ArgumentCaptor.forClass(Company.class);
        Evaluation result = companyService.startEvaluation(company.getCompanyId());
        verify(companyRepository, times(1)).save(companyCaptor.capture());
        assertNotNull(result);
        assertEquals("1", result.getEvaluationId());
        assertEquals(2, companyCaptor.getValue().getEvaluations().size());
    }

    @Test
    void testGetCompanyOut(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        when(evaluationService.getRecentCompletedEvaluation(company.getEvaluations())).thenReturn(createEvaluation());
        when(evaluationService.getCompletedEvaluationsByIds(company.getEvaluations())).thenReturn(List.of(createEvaluation()));
        CompanyOutDTO result = companyService.getCompanyOut(company.getCompanyId());
        assertNotNull(result);
        assertEquals(1, result.getEvaluationHistory().size());
        assertEquals(1, result.getEvaluationHistory().get(0).getDimensionResults().get(0).getLevelValue());
        assertNotNull(result.getCurrentEvaluation());
        assertEquals(1, result.getCurrentEvaluation().get(0).getLevelValue());
    }

    @Test
    void testGetCompanyOutNotFound(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.empty());
        try {
            companyService.getCompanyOut(company.getCompanyId());
        } catch (Exception e) {
            assertEquals("Company not found", e.getMessage());
        }
    }

    @Test
    void testGetCompanyInfo(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        CompanyInfoDTO result = companyService.getCompanyInfo(company.getCompanyId());
        assertNotNull(result);
        assertEquals(company.getName(), result.getName());
        assertEquals(company.getEmployees(), result.getNumberEmployees());
        assertEquals(company.getNit(), result.getNit());
        assertEquals(company.getAddress(), result.getAddress());
        assertEquals(company.getLegalRep(), result.getLegalRep());
        assertEquals(company.getLegalRepEmail(), result.getLegalRepEmail());

    }

    @Test
    void testGetCompanyInfoNotFound(){
        Company company = createCompany();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.empty());
        try {
            companyService.getCompanyInfo(company.getCompanyId());
        } catch (Exception e) {
            assertEquals("Company not found", e.getMessage());
        }
    }

    @Test
    void testGetCompanyByEmail(){
        when(companyRepository.findByLegalRepEmail("company@email.com")).thenReturn(Optional.of(createCompany()));
        Company result = companyService.getCompanyByEmail("company@email.com");
        assertNotNull(result);
    }

    @Test
    void testGetCompanyByEmailNotFound(){
        when(companyRepository.findByLegalRepEmail("company@email.com")).thenReturn(Optional.empty());
        try{
            companyService.getCompanyByEmail("company@email.com");
        }catch (Exception e){
            assertEquals("Company not found", e.getMessage());
        }
    }

    @Test
    void testSetCompanyInfo(){
        Company company = createCompany();
        CompanyInfoDTO companyInfo = CompanyInfoDTO.builder()
                .address("Carrera32")
                .legalRepEmail("otro@email.com")
                .legalRep("Juanita")
                .legalRepTel("3167923478")
                .nit("2032030345")
                .tel("20000003")
                .name("Emcali")
                .numberEmployees(20)
                .build();
        when(companyRepository.findById(company.getCompanyId())).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenAnswer(invocation -> invocation.getArgument(0));
        Company result = companyService.setCompanyInfo(company.getCompanyId(), companyInfo);
        assertNotNull(result);
        assertEquals(companyInfo.getAddress(), result.getAddress());
        assertEquals(companyInfo.getLegalRepEmail(), result.getLegalRepEmail());
        assertEquals(companyInfo.getLegalRep(), result.getLegalRep());
        assertEquals(companyInfo.getLegalRepTel(), result.getLegalRepTel());
        assertEquals(companyInfo.getNit(), result.getNit());
        assertEquals(companyInfo.getTel(), result.getTel());
        assertEquals(companyInfo.getName(), result.getName());
        assertEquals(companyInfo.getNumberEmployees(), result.getEmployees());

    }

    Company createCompany(){
        return Company.builder()
                .companyId("companyId")
                .legalRepEmail("company@email.com")
                .password("company")
                .employees(20)
                .companyType(CompanyType.builder().companyTypeId("1").build())
                .address("Carrera24")
                .name("Emcali")
                .economicSectorId("4")
                .cityId("1")
                .nit("2032030345")
                .legalRep("Juan")
                .evaluations(List.of("1"))
                .build();
    }

    RegisterDTO createRegisterDTO(){
        return RegisterDTO.builder()
                .legalRepEmail("company@email.com")
                .password("company")
                .numberEmployees(20)
                .address("Carrera24")
                .name("Emcali")
                .economicSector(1)
                .city(1)
                .type(1)
                .nit("2032030345")
                .legalRep("Juan")
                .build();
    }

    CompanyDTO createCompanyDTO(){
        return CompanyDTO.builder()
                .employees(20)
                .companyType(CompanyType.builder().companyTypeId("1").build())
                .address("Carrera24")
                .name("Emcali")
                .economicSectorId("4")
                .build();
    }

    Evaluation createEvaluation(){
        return Evaluation.builder()
                .date(LocalDateTime.parse("2021-08-01T00:00:00"))
                .evaluationId("1")
                .dimensionResults(List.of(DimensionResult.builder().dimensionId("1").levelValue(1).build()))
                .build();
    }
}
