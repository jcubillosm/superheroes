package com.superheroes.heroes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
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
	    private SuperheroesServiceImpl superheroesService;

	    @Mock
	    private SuperheroesRepository superheroesRepository;

	    @Test
	    void whenGetAllSuperheroesService_returnHeroesList() throws Exception  {
	    	List<Superheroe> heroesList = new ArrayList<Superheroe>();
	    	heroesList.add(new Superheroe(1L,"Hulk"));
	    	heroesList.add(new Superheroe(2L,"Superman"));
	        when(superheroesRepository.findAll()).thenReturn(heroesList);

	        List<Superheroe> heroesdb = superheroesService.getAllSuperheroes();

	        assertEquals(heroesList, heroesdb);
	        verify(superheroesRepository).findAll();
	    }
	    @Test
	    void whenGetSuperheroedByIdService_returnHeroe() throws Exception  {
	    	Long id = 1L;
	    	String name = "Hulk";
	    	Superheroe heroeByIdSuperheroe = new Superheroe(id,"Hulk");
	    	
	    	when(superheroesRepository.findById(anyLong())).thenReturn(Optional.of(heroeByIdSuperheroe));
	    	Superheroe heroesdb = superheroesService.getSuperheroeById(id);
			assertEquals(heroesdb.getName(), name);	    
	    
	        verify(superheroesRepository).findById(id);
	    }

	    @Test
	    void whenGetSuperheroedByIdService_returnException() throws Exception  {
	    	Long id = 20L;
	        when(superheroesRepository.findById(id)).thenThrow(new ResourceNotFoundException("NOT_FOUND_BY_ID"));

	        Exception heroesdb = assertThrows(ResourceNotFoundException.class, () -> superheroesService.getSuperheroeById(id));
	        assertEquals(ResourceNotFoundException.class, heroesdb.getClass());

	        verify(superheroesRepository).findById(id);
	    }	
	    @Test
	    void whenGetSuperheroeByPatternService_returnHeroesList() {
	    	String pattern = "man";
	       	List<Superheroe> heroesList = new ArrayList<Superheroe>();
	    	heroesList.add(new Superheroe(1L,"Hulk"));
	    	heroesList.add(new Superheroe(2L,"Superman"));
	        when(superheroesRepository.findByPattern(argThat(arg -> arg.equals("%" + pattern + "%")))).thenReturn(heroesList);

	        List<Superheroe> heroesdb = superheroesService.getSuperheroeByPattern(pattern);

	        assertEquals(heroesList, heroesdb);
	        verify(superheroesRepository).findByPattern("%" + pattern + "%");
	    }
	    @Test
	    void whenUpdateSuperheroeService_returnOk() {
	    	Superheroe heroe = new Superheroe(8L, "Lobezno");

	        when(superheroesRepository.save(argThat(arg -> arg.getId().equals(8L)))).thenReturn(heroe);

	        assertEquals(heroe, superheroesService.updateSuperheroe(heroe));
	        verify(superheroesRepository).save(heroe);
	    }

}
