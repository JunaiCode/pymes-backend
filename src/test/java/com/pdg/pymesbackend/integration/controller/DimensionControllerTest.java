package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DimensionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createDimension() throws Exception{

    }

    @Test
    public void getDimension() throws Exception{

    }

    @Test
    public void getAllDimensions() throws Exception{

    }

    @Test
    public void updateDimension() throws Exception{

    }

    @Test
    public void deleteDimension() throws Exception{

    }

}
