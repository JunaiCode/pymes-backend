package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.*;
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
public class ModelControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    final String modelId = "modelId";
    @Test
    public void createModel() throws Exception{
        ArrayList<String> versions = new ArrayList<>();
        versions.add("Version1Id");
        var result = mvc.perform(MockMvcRequestBuilders.post("/model/add").content(
                        mapper.writeValueAsString(new ModelDTO("Modelo Nuevo","Descripcion Modelo Nuevo",versions,false))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void addVersion() throws Exception{
        ArrayList<String> questions = new ArrayList<>();
        questions.add("questionProcess2");
        LevelDTO newLevel = LevelDTO.builder().value(2).questions(questions).name("Nuevo Level").description("Nivel").build();
        ArrayList<LevelDTO> levelDTOS = new ArrayList<>();
        levelDTOS.add(newLevel);
        DimensionDTO newDimension = DimensionDTO.builder().description("Nuevo").name("Dimension Nueva").levels(levelDTOS).build();
        ArrayList<DimensionDTO> dimensionDTOS = new ArrayList<>();
        dimensionDTOS.add(newDimension);
        var result = mvc.perform(MockMvcRequestBuilders.post("/model/add/version/{modelId}",modelId).content(
                        mapper.writeValueAsString(new VersionDTO("Version Nueva",dimensionDTOS,true))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getAll() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/model/get/all").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getById() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/model/get/{id}",modelId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getVersionsByModelId() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/model/get/versions/{id}",modelId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
