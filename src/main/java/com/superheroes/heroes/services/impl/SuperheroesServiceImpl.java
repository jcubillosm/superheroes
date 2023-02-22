package com.superheroes.heroes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
	@Cacheable("superheroes")
	public List<Superheroe> getAllSuperheroes() {
	    return superheroesRepository.findAll();
	}
	
    @Override
    @Cacheable("superheroe")
    public Superheroe getSuperheroeById(Long id) {
        return superheroesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Superhero with id: " + id + " not found ")));
    }
    
    @Override
    @Cacheable("superheroe")
    public List<Superheroe> getSuperheroeByPattern(String pattern) {
    	return superheroesRepository.findByPattern(String.format("%%%s%%", pattern));
    	
    }
    @Override 
    @Caching(evict = {
    	      @CacheEvict(value="superheroe", allEntries=true),
    	      @CacheEvict(value="superheroes", allEntries=true)})
    public Superheroe modifySuperheroe(Superheroe superheroe) {
        return (superheroesRepository.save(superheroe));
    }
    @Override
    @Caching(evict = {
    	      @CacheEvict(value="superheroe", allEntries=true),
    	      @CacheEvict(value="superheroes", allEntries=true)})
    public void deleteById(Long id) {
    	superheroesRepository.deleteById(id);
    }

    
}
