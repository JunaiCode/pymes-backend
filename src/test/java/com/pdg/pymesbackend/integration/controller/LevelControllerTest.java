package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.CompanyDTO;
import com.pdg.pymesbackend.dto.LevelDTO;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfigurationData.class)
public class LevelControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    String dimensionId = "dimensionTechId";

    @Test
    public void createLevel() throws Exception{
        ArrayList<String> questions = new ArrayList<>();
        questions.add("questionTecnologia1Id");
        LevelDTO newLevel = LevelDTO.builder().value(3).questions(questions).name("Nuevo Level").description("Nivel").build();
        var result = mvc.perform(MockMvcRequestBuilders.post("/level/add/{dimensionId}",dimensionId).content(
                        mapper.writeValueAsString(newLevel)
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getLevelsInDimension() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/level/dimension/{dimensionId}",dimensionId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
    
}
