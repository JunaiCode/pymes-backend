package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.dto.LoginOutDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.LoginInDTO;
import com.pdg.pymesbackend.model.Admin;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.model.CompanyType;
import com.pdg.pymesbackend.repository.AdminRepository;
import com.pdg.pymesbackend.service.modules.CompanyService;
import com.pdg.pymesbackend.service.modules.ModelService;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.modules.implementations.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    AdminRepository adminRepository;
    @Mock
    CompanyService companyService;
    @Mock
    VersionService versionService;
    @Mock
    ModelService modelService;
    @InjectMocks
    AuthServiceImpl authService;

    @Test
    void testLoginAdmin() {
        Admin admin = createAdmin();
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        LoginInDTO loginInDTO = LoginInDTO.builder()
                .email(admin.getEmail())
                .password(admin.getPassword())
                .build();
        LoginOutDTO loginOutDTO = authService.login(loginInDTO);
        assertEquals(admin.getEmail(), loginOutDTO.getEmail());
        assertEquals(admin.getId(), loginOutDTO.getId());
        assertEquals("admin", loginOutDTO.getRole());
    }

    @Test
    void testLoginCompany() {
        Company company = createCompany();
        LoginInDTO loginInDTO = LoginInDTO.builder()
                .email(company.getLegalRepEmail())
                .password(company.getPassword())
                .build();
        when(adminRepository.findByEmail(loginInDTO.getEmail())).thenReturn(Optional.empty());
        when(companyService.getCompanyByEmail(loginInDTO.getEmail())).thenReturn(company);
        when(versionService.getActualVersion()).thenReturn("1");

        LoginOutDTO loginOutDTO = authService.login(loginInDTO);
        assertEquals(company.getLegalRepEmail(), loginOutDTO.getEmail());
        assertEquals(company.getCompanyId(), loginOutDTO.getId());
        assertEquals("company", loginOutDTO.getRole());
    }

    @Test
    void testRegister() {
        RegisterDTO registerDTO = createRegisterDTO();
        Company company = createCompany();
        when(companyService.save(registerDTO)).thenReturn(company);
        LoginOutDTO login = authService.register(registerDTO);
        assertEquals(company.getLegalRepEmail(), login.getEmail());
        assertEquals(company.getCompanyId(), login.getId());

    }

    Admin createAdmin() {
        return Admin.builder()
                .id("1")
                .name("admin")
                .email("admin@email.com")
                .password("admin")
                .build();

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
                .nit("2032030345")
                .legalRep("Juan")
                .build();
    }
}
