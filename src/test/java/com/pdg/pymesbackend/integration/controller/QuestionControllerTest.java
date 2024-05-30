package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.QuestionDTO;
import com.pdg.pymesbackend.dto.RecommendationDTO;
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
public class QuestionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    String questionId = "questionTecnologia2Id";

    String tagId = "tagProcessId";

    String levelId = "level2ProcessId";

    @Test
    public void createQuestion() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.post("/question/add").content(
                        mapper.writeValueAsString(new QuestionDTO("Nueva Pregunta",2.0,10,new ArrayList<>(), new RecommendationDTO("Recomendacion",new ArrayList<>(),"Recomendacion Title"),"Version1Id",
                                "dimensionTechId","tagTechId",1,"level1TechId"))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void deleteQuestion() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.delete("/question/delete/{id}",questionId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getQuestions() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/question/get/all").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getQuestion() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/question/get/{id}",questionId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getQuestionsByTag() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/question/get/tag/{tag}",tagId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getQuestionsByLevel() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/question/get/level/{level}",levelId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
