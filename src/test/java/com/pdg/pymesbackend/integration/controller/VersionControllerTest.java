package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.DimensionQuestionInDTO;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.RecommendationDTO;
import com.pdg.pymesbackend.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfigurationData.class)
public class VersionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    String versionId = "Version1Id";

    String companyTypeId="MICRO";

    String level2ProcessId = "level2ProcessId";

    String dimensionProcessId = "dimensionProcessId";

    @Test
    public void get() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/version/get/{id}",versionId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getQuestionsFirstLevel() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/version/get/{id}/questions/{companyTypeId}/first-level",versionId,companyTypeId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getQuestionsByLevel() throws Exception{
        DimensionQuestionInDTO dto  = DimensionQuestionInDTO.builder().dimensionId(dimensionProcessId).levelId(level2ProcessId).versionId(versionId).companyTypeId(companyTypeId).build();
        var result = mvc.perform(MockMvcRequestBuilders.post("/version/get/questions/company-type/level")
                .content(
                        mapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void createQuestion() throws Exception{
        QuestionDTO questionDTO = new QuestionDTO("Nueva Pregunta",2.0,10,new ArrayList<>(), new RecommendationDTO("Recomendacion",new ArrayList<>(),"Recomendacion Title"),"Version1Id",
                "dimensionTechId","tagTechId",1,"level1TechId");
        var result = mvc.perform(MockMvcRequestBuilders.post("/version/add/question")
                .content(
                        mapper.writeValueAsString(questionDTO)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
