package com.pdg.pymesbackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdg.pymesbackend.TestConfigurationData;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfigurationData.class)
public class TagControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    String dimensionTechId = "dimensionTechId";

    String dimensionProcessId = "dimensionProcessId";

    String tagProcessId = "tagProcessId";
    @Test
    public void save() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.post("/tag/add").content(
                        mapper.writeValueAsString(new TagDTO("Software2","New Tag Software",dimensionTechId))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void update() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.put("/tag/update/{id}",tagProcessId).content(
                        mapper.writeValueAsString(new TagDTO("Nuevo Nombre","New Tag Process",dimensionProcessId))
                ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void dimensionTags() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/tag/get/dimension/{dimensionId}",dimensionTechId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getTag() throws Exception{
        var result = mvc.perform(MockMvcRequestBuilders.get("/tag/get/{tagId}",tagProcessId).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
