package com.superheroes.heroes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superheroes.heroes.exceptions.ResourceNotFoundException;
import com.superheroes.heroes.models.Superheroe;
import com.superheroes.heroes.repository.SuperheroesRepository;
import com.superheroes.heroes.services.SuperheroesService;

@Service
public class SuperheroesServiceImpl implements SuperheroesService{

	@Autowired
	SuperheroesRepository repository;
	@Override
	@Transactional(readOnly = true)
	public List<Superheroe> getAllSuperheroes() {
	    return this.repository.findAll();
	}
	
    @Override
    @Transactional(readOnly = true)
    public Superheroe getSuperheroedById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Super Hero not found with id %d", id)));
    }
}
