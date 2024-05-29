package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfigurationData.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createQuestion() throws Exception{
        System.out.println();
    }

    @Test
    public void addOption() throws Exception{

    }

    @Test
    public void deleteQuestion() throws Exception{

    }

    @Test
    public void getQuestions() throws Exception{

    }

    @Test
    public void getQuestion() throws Exception{

    }

    @Test
    public void getQuestionsByTag() throws Exception{

    }

    @Test
    public void getQuestionsByLevel() throws Exception{

    }

}
