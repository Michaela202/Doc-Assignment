package com.example.my_spring_boot_app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.my_spring_boot_app.model.ViaTabloid;
import com.example.my_spring_boot_app.service.TabloidService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TabloidController.class)
public class TabloidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TabloidService service;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTabloids() throws Exception {
        ViaTabloid tabloid1 = new ViaTabloid();
        tabloid1.setId(1L);
        tabloid1.setTitle("Title 1");

        ViaTabloid tabloid2 = new ViaTabloid();
        tabloid2.setId(2L);
        tabloid2.setTitle("Title 2");

        when(service.getAllTabloids()).thenReturn(Arrays.asList(tabloid1, tabloid2));

        mockMvc.perform(get("/api/tabloids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[1].title").value("Title 2"));
    }

    @Test
    public void testGetTabloidById() throws Exception {
        ViaTabloid tabloid = new ViaTabloid();
        tabloid.setId(1L);
        tabloid.setTitle("Title 1");

        when(service.getTabloidById(1L)).thenReturn(Optional.of(tabloid));

        mockMvc.perform(get("/api/tabloids/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title 1"));
    }

    @Test
    public void testCreateTabloid() throws Exception {
        ViaTabloid tabloid = new ViaTabloid();
        tabloid.setTitle("New Tabloid");

        when(service.createTabloid(any(ViaTabloid.class))).thenReturn(tabloid);

        mockMvc.perform(post("/api/tabloids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tabloid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Tabloid"));
    }

    @Test
    public void testUpdateTabloid() throws Exception {
        ViaTabloid tabloid = new ViaTabloid();
        tabloid.setId(1L);
        tabloid.setTitle("Updated Tabloid");

        when(service.getTabloidById(1L)).thenReturn(Optional.of(tabloid));
        when(service.updateTabloid(any(ViaTabloid.class))).thenReturn(tabloid);

        mockMvc.perform(put("/api/tabloids/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tabloid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Tabloid"));
    }

    @Test
    public void testDeleteTabloid() throws Exception {
        mockMvc.perform(delete("/api/tabloids/1"))
                .andExpect(status().isNoContent());
    }
}
