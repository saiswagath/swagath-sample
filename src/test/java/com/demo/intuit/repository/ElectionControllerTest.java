package com.demo.intuit.repository;


import com.demo.intuit.controller.ElectionController;
import com.demo.intuit.model.election.Citizen;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@WebMvcTest(controllers = ElectionController.class)
public class ElectionControllerTest {

    @MockBean
    ElectionRepository electionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should List All Posts when making GET Request to - /api/getAllCitizens")
    public void shouldFetchAllCitizens() throws Exception{

        Citizen citizen1 = new Citizen("Swagath", true);
        Citizen citizen2 = new Citizen("Sai", false);

        Mockito.when(electionRepository.getAllCitizens()).thenReturn(Arrays.asList(citizen1,citizen2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllCitizens"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)));
    }
}
