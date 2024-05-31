package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.DimensionDTO;
import com.pdg.pymesbackend.dto.LevelDTO;
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
public class DimensionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    final String versionId = "Version1Id";

    final String questionId = "questionProcess1Id";

    final String dimensionId = "dimensionTechId";

    final String dimensionIdDeleted = "dimensionProcessId";
    @Test
    public void createDimension() throws Exception{
        ArrayList<String> questions = new ArrayList<>();
        questions.add(questionId);
        ArrayList<LevelDTO> levels = new ArrayList<>();
        levels.add(new LevelDTO("Level Nuevo","Descripcion new level",2,questions));
        var result = mvc.perform(MockMvcRequestBuilders.post("/dimension/add/{versionId}",versionId).content(
                        mapper.writeValueAsString(new DimensionDTO("Nueva Dimension","Esta es una nueva dimension",levels
                               ))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getDimension() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/dimension/get/{dimensionId}",dimensionId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void updateDimension() throws Exception{
        ArrayList<String> questions = new ArrayList<>();
        questions.add(questionId);
        ArrayList<LevelDTO> levels = new ArrayList<>();
        levels.add(new LevelDTO("Level Nuevo","Descripcion new level",2,questions));
        var result = mvc.perform(MockMvcRequestBuilders.put("/dimension/update/{id}",dimensionId).content(
                mapper.writeValueAsString(new DimensionDTO("Dimension Editada","Esta es una edicion de la dimension",levels
                        ))).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void deleteDimension() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.delete("/dimension/delete/{id}",dimensionIdDeleted).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
