package com.superheroes.heroes.controllers;


import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private SuperheroesService superheroesService;
    private ObjectMapper mapper;
    private final String BASE_URL = "/superheroes";
    
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
    	
    	
        when(superheroesService.getAllSuperheroes()).thenReturn(heroesList);

        mockMvc.perform(get(BASE_URL)
                .contentType(APPLICATION_JSON))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Hulk"))
                .andExpect(jsonPath("$[1].name").value("Superman"))
                .andExpect(jsonPath("$", hasSize(tam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void whenGetSuperheroedById_returnHeroe() throws Exception {
    	Long id = 1L;
    	String name = "Hulk";
    	Superheroe heroeByIdSuperheroe = new Superheroe(1L,"Hulk");
    	
        when(superheroesService.getSuperheroeById(id)).thenReturn(heroeByIdSuperheroe);

        mockMvc.perform(get(BASE_URL +"/"+ id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(name));
    }
    @Test
    void whenGetSuperheroedById_returnException() throws Exception  {
    	Long id = 20L;
    	when(superheroesService.getSuperheroeById(id)).thenThrow(new ResourceNotFoundException("NOT_FOUND_BY_ID "));

        Exception heroe = assertThrows(ResourceNotFoundException.class, () -> superheroesService.getSuperheroeById(id));
        assertEquals(ResourceNotFoundException.class, heroe.getClass());
    }
    @Test
    void whenGetSuperheroeByPattern_returnHeroesList() throws Exception {
    	String pattern = "man";
       	List<Superheroe> heroesList = new ArrayList<Superheroe>();
    	heroesList.add(new Superheroe(1L,"Hulk"));
    	heroesList.add(new Superheroe(2L,"Superman"));
        when(superheroesService.getSuperheroeByPattern(argThat(pattern::equals))).thenReturn(heroesList);

        mockMvc.perform(get(BASE_URL + "/findByPattern/" + pattern)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Hulk"))
                .andExpect(jsonPath("$[1].name").value("Superman"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(this.mapper.writeValueAsString(heroesList)));
    }
    
    @Test
    void whenModifySuperheroe_returnOk() throws Exception {
    	Superheroe heroe = new Superheroe(8L, "Lobezno");

        when(superheroesService.modifySuperheroe(argThat(arg -> arg.getId().equals(8L)))).thenReturn(heroe);

        mockMvc.perform(put(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(heroe)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Lobezno"))
                .andExpect(content().json(this.mapper.writeValueAsString(heroe))).andDo(print());
    }
    @Test
    void whenDeleteById_returnNoContent() throws Exception {
        doNothing().when(superheroesService).deleteById(any());

        mockMvc.perform(delete(BASE_URL+"/7")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    
}
