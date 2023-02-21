package com.superheroes.heroes.services;

import java.util.List;

import com.superheroes.heroes.models.Superheroe;

public interface SuperheroesService {

	List<Superheroe> getAllSuperheroes();
	
	Superheroe getSuperheroeById(Long id);
	
	List<Superheroe> getSuperheroeByPattern(String pattern);
	
	Superheroe modifySuperheroe(Superheroe superheroe);
	
	void deleteById(Long id);
}
