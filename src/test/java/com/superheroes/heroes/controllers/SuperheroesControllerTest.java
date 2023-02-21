package com.superheroes.heroes.controllers;


import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superheroes.heroes.exceptions.ResourceNotFoundException;
import com.superheroes.heroes.models.Superheroe;
import com.superheroes.heroes.services.SuperheroesService;

@WebMvcTest(SuperheroesController.class)
public class SuperheroesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SuperheroesService service;
    private ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        this.mapper = new ObjectMapper();
    }
    @Test
    void whenGetAllSuperheroes_returnHeroesList() throws Exception {
    	int tam = 2;
    	List<Superheroe> heroesList = new ArrayList<Superheroe>();
    	heroesList.add(new Superheroe(1L,"Hulk"));
    	heroesList.add(new Superheroe(2L,"Superman"));
    	
    	
        when(this.service.getAllSuperheroes()).thenReturn(heroesList);

        mockMvc.perform(get("/superheroes")
                .contentType(APPLICATION_JSON))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Hulk"))
                .andExpect(jsonPath("$[1].name").value("Superman"))
                .andExpect(jsonPath("$", hasSize(tam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()).andDo(print());
    }
    @Test
    void whenGetSuperheroedById_returnHeroe() throws Exception {
    	
        when(this.service.getSuperheroedById(1L)).thenReturn(new Superheroe(1L,"Hulk"));

        mockMvc.perform(get("/superheroes/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Hulk"));

        verify(this.service).getSuperheroedById(1L);
    }
    @Test
    void whenGetSuperheroedById_returnException() throws Exception  {
    	when(this.service.getSuperheroedById(20L)).thenThrow(new ResourceNotFoundException("NOT_FOUND_BY_ID "));

        Exception heroe = assertThrows(ResourceNotFoundException.class, () -> this.service.getSuperheroedById(20L));
        assertEquals(ResourceNotFoundException.class, heroe.getClass());

        verify(this.service).getSuperheroedById(20L);
    }
}
