package com.pdg.pymesbackend.unit;

import com.pdg.pymesbackend.repository.AdminRepository;
import com.pdg.pymesbackend.service.modules.CompanyService;
import com.pdg.pymesbackend.service.modules.ModelService;
import com.pdg.pymesbackend.service.modules.VersionService;
import com.pdg.pymesbackend.service.modules.implementations.AuthServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
