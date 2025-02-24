package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import com.pdg.pymesbackend.dto.EvaluationResultDTO;
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
public class EvaluationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    final String companyId = "companyId3";
    final String evaluationId = "Evaluation1Id";

    final String Version1Id = "Version1Id";

    final String evaluationCompletedId ="EvaluationCompletedId";
    @Test
    public void createEvaluation() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.post("/evaluation/add/{companyId}",companyId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void finishEvaluation() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.post("/evaluation/finish/{evaluationId}/version/{versionId}",evaluationId,Version1Id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getEvaluation() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/evaluation/{evaluationId}",evaluationId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void addAnswers() throws Exception {
        ArrayList<EvaluationResultDTO> evaluationResultDTOS = new ArrayList<>();
        EvaluationResultDTO evaluation = EvaluationResultDTO.builder().marked(false).questionId("questionTecnologia1Id").optionId("optionTecnologia1").build();
        EvaluationResultDTO evaluation2 = EvaluationResultDTO.builder().marked(true).questionId("questionProcess1Id").optionId("optionProcesos2").build();
        evaluationResultDTOS.add(evaluation);
        evaluationResultDTOS.add(evaluation2);
        var result = mvc.perform(MockMvcRequestBuilders.post("/evaluation/{evaluationId}/addAnswers",evaluationCompletedId).content(
                mapper.writeValueAsString(evaluationResultDTOS)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
