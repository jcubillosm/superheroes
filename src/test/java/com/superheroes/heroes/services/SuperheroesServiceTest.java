package com.superheroes.heroes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.superheroes.heroes.exceptions.ResourceNotFoundException;
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
	    void whenGetAllSuperheroesService_returnHeroesList() throws Exception  {
	    	List<Superheroe> heroesList = new ArrayList<Superheroe>();
	    	heroesList.add(new Superheroe(1L,"Hulk"));
	    	heroesList.add(new Superheroe(2L,"Superman"));
	        when(repository.findAll()).thenReturn(heroesList);

	        List<Superheroe> heroesdb = this.service.getAllSuperheroes();

	        assertEquals(heroesList, heroesdb);
	        verify(repository).findAll();
	    }
	    @Test
	    void whenGetSuperheroedByIdService_returnHeroe() throws Exception  {
	    	
	    	when(repository.findById(anyLong())).thenReturn(Optional.of(new Superheroe(1L, "Hulk")));
	    	Superheroe heroesdb = service.getSuperheroeById(1L);
			assertEquals(heroesdb.getName(), "Hulk");	    
	    
	        verify(this.repository).findById(1L);
	    }

	    @Test
	    void whenGetSuperheroedByIdService_returnException() throws Exception  {
	        when(this.repository.findById(20L)).thenThrow(new ResourceNotFoundException("NOT_FOUND_BY_ID"));

	        Exception heroesdb = assertThrows(ResourceNotFoundException.class, () -> this.service.getSuperheroeById(20L));
	        assertEquals(ResourceNotFoundException.class, heroesdb.getClass());

	        verify(this.repository).findById(20L);
	    }	    

}
