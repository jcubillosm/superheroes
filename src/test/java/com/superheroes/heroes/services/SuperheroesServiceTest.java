package com.superheroes.heroes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.superheroes.heroes.models.Superheroe;
import com.superheroes.heroes.repository.SuperheroesRepository;
import com.superheroes.heroes.services.impl.SuperheroesServiceImpl;

@SpringBootTest
public class SuperheroesServiceTest {
	 @InjectMocks
	    private SuperheroesServiceImpl service;

	    @Mock
	    private SuperheroesRepository repository;

	    @Test
	    void whenGetAllSuperheroesService_returnHeroesList() {
	    	List<Superheroe> heroesList = new ArrayList<Superheroe>();
	    	heroesList.add(new Superheroe(1L,"Hulk"));
	    	heroesList.add(new Superheroe(2L,"Superman"));
	        when(this.repository.findAll()).thenReturn(heroesList);

	        List<Superheroe> heroesdb = this.service.getAllSuperheroes();

	        assertEquals(heroesList, heroesdb);
	        verify(this.repository).findAll();
	    }
}
