package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.CompanyInfoDTO;
import com.pdg.pymesbackend.model.CompanyType;
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
public class CompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    String companyId = "companyId";

    String companyId3 ="companyId3";


    @Test
    public void getCompany() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.get("/company/get/{id}", companyId3)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void createCompany() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.post("/company/add").content(
                        mapper.writeValueAsString(new CompanyDTO("Emcali","Carrera 4a","emcali@hotmail.com",
                                20,new CompanyType("MICRO","Micro empresa","MicroEmpresa"),"1"))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getInfoCompany() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.get("/company/get/info/{id}", companyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void setInfoCompany() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.post("/company/set/info/{id}", companyId).content(
                        mapper.writeValueAsString(new CompanyInfoDTO("Carrera 24N","Ramirez Casares","ramirez@gmail.com"
                                ,"3156970102","Sura","1239401234",200,"3152199042"))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void checkUncompletedEvaluation() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.get("/company/{companyId}/results", companyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void startEvaluation() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.post("/company/{companyId}/evaluation", companyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getActualActionPlan() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.get("/company/{companyId}/actionPlan/actual", companyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
