package com.superheroes.heroes.controllers;


import static com.w2m.superheroes.data.Data.MANOLITO;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.argThat;
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
    void whenFindById_returnHeroe() throws Exception {
    	
        when(this.service.findById(argThat(arg -> arg.equals(3L)))).thenReturn(MANOLITO());

        this.mvc.perform(get("/super-heroes/3")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Manolito el fuerte"));

        verify(this.service).findById(3L);
    }
}
