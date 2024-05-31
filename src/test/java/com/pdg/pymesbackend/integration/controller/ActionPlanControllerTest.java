package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.DateDTO;
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
public class ActionPlanControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    final String evaluationId = "EvaluationCompletedId";
    final String actionPlanId = "ActionPlan1Id";

    final String recommendationActionPlanId = "RecommendationPlanId1";
    @Test
    public void createActionPlan() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.post("/actionPlan/add/evaluation/{evaluationId}",evaluationId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void updateEndDate() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.put("/actionPlan/updateEnd/{actionPlanId}",actionPlanId).content(
                mapper.writeValueAsString(new DateDTO("2024-05-31T05:00:00.000"))).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void updateStepTrack() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.put("/actionPlan/updateStepTrack/{actionPlanId}/{recommendationActionPlanId}/{completed}",actionPlanId,recommendationActionPlanId,false).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
