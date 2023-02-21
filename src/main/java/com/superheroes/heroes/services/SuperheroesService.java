package com.superheroes.heroes.services;

import java.util.List;

import com.superheroes.heroes.models.Superheroe;

public interface SuperheroesService {

	List<Superheroe> getAllSuperheroes();
	
	Superheroe getSuperheroedById(Long id);
	
	
}
