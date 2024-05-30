package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.LoginInDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfigurationData.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void loginAdmin() throws Exception{
        LoginInDTO loginAdmin = LoginInDTO.builder().email("admin@admin.com").password("password").build();
        var result = mvc.perform(MockMvcRequestBuilders.post("/auth/login").content(
                        mapper.writeValueAsString(loginAdmin)
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void loginCompany() throws Exception{
        LoginInDTO loginCompany = LoginInDTO.builder().email("juan@hotmail.com").password("123").build();
        var result = mvc.perform(MockMvcRequestBuilders.post("/auth/login").content(
                        mapper.writeValueAsString(loginCompany)
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void register() throws Exception{
        RegisterDTO register = RegisterDTO.builder()
                .address("123 Main Street")
                .city(1)
                .economicSector(3)
                .expectations("Increase market share")
                .legalRep("John Doe")
                .legalRepEmail("johndoe@example.com")
                .legalRepTel("123-456-7890")
                .name("Tech Corp")
                .nit("123456789")
                .numberEmployees(50)
                .opsYears("10")
                .password("securepassword123")
                .specificNeeds("Access to advanced research tools")
                .tel("098-765-4321")
                .termsAndConditions(true)
                .type(2)
                .build();
        var result = mvc.perform(MockMvcRequestBuilders.post("/auth/register").content(
                        mapper.writeValueAsString(register)
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
