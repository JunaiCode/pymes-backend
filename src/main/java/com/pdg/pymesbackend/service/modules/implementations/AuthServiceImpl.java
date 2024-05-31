package com.pdg.pymesbackend.service.modules.implementations;

import com.pdg.pymesbackend.dto.LoginOutDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.LoginInDTO;
import com.pdg.pymesbackend.model.Admin;
import com.pdg.pymesbackend.model.Company;
import com.pdg.pymesbackend.repository.AdminRepository;
import com.pdg.pymesbackend.service.modules.AuthService;
import com.pdg.pymesbackend.service.modules.CompanyService;
import com.pdg.pymesbackend.service.modules.ModelService;
import com.pdg.pymesbackend.service.modules.VersionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    AdminRepository adminRepository;
    CompanyService companyService;
    VersionService versionService;
    ModelService modelService;

    public LoginOutDTO login(LoginInDTO loginInDTO) {
        //Check if the user is admin

        Optional<Admin> admin = adminRepository.findByEmail(loginInDTO.getEmail());
        if(admin.isPresent()){
            //Check if the password is correct
            if(admin.get().getPassword().equals(loginInDTO.getPassword())){
                //Return the token
                return LoginOutDTO.builder()
                        .email(admin.get().getEmail())
                        .id(admin.get().getId())
                        .role("admin")
                        .build();
            }
        }
        String versionId = versionService.getActualVersion();
        //Check if the user is a company
        Company company = companyService.getCompanyByEmail(loginInDTO.getEmail());
        if(company != null){
            //Check if the password is correct
            if(company.getPassword().equals(loginInDTO.getPassword())){


                //Return the token
                return LoginOutDTO.builder()
                        .email(company.getLegalRepEmail())
                        .id(company.getCompanyId())
                        .role("company")
                        .company(company.getName())
                        .actualVersion(versionId)
                        .companyType(company.getCompanyType().getCompanyTypeId())
                        .build();
            }
        }
        return null;
    }

    public LoginOutDTO register(RegisterDTO registerDTO) {
        String versionId = modelService.getActualVersion();
        Company newComp = companyService.save(registerDTO);
        return LoginOutDTO.builder()
                .email(newComp.getLegalRepEmail())
                .id(newComp.getCompanyId())
                .role("company")
                .company(newComp.getName())
                .actualVersion(versionId)
                .build();

    }
}
