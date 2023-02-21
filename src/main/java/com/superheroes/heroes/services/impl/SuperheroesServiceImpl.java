package com.superheroes.heroes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superheroes.heroes.exceptions.ResourceNotFoundException;
import com.superheroes.heroes.models.Superheroe;
import com.superheroes.heroes.repository.SuperheroesRepository;
import com.superheroes.heroes.services.SuperheroesService;

@Service
public class SuperheroesServiceImpl implements SuperheroesService{

	@Autowired
	SuperheroesRepository superheroesRepository;
	@Override
	public List<Superheroe> getAllSuperheroes() {
	    return superheroesRepository.findAll();
	}
	
    @Override
    public Superheroe getSuperheroeById(Long id) {
        return superheroesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Superhero with id: " + id + " not found ")));
    }
    
    @Override
    public List<Superheroe> getSuperheroeByPattern(String pattern) {
    	return superheroesRepository.findByPattern(String.format("%%%s%%", pattern));
    	
    }
    @Override   
    public Superheroe updateSuperheroe(Superheroe superheroe) {
        return (superheroesRepository.save(superheroe));
    }


    
}
