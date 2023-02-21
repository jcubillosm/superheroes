package com.superheroes.heroes.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.superheroes.heroes.models.Superheroe;
import com.superheroes.heroes.services.SuperheroesService;

@RestController
@RequestMapping("/superheroes")
public class SuperheroesController {
	@Autowired
	SuperheroesService superheroesService;
	
    @GetMapping
    @ResponseStatus(OK)
    public List<Superheroe> getAllSuperheroes() {
        return superheroesService.getAllSuperheroes();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSuperheroeById(@PathVariable Long id) {
        Superheroe heroe = superheroesService.getSuperheroeById(id);
        return ResponseEntity.ok(heroe);
    }
    @GetMapping("/findByPattern/{pattern}")
    public List<Superheroe> getSuperheroeByPattern(@PathVariable String pattern) {
        return superheroesService.getSuperheroeByPattern(pattern);
    }
    @PutMapping
    @ResponseStatus(OK)
    public Superheroe updateSuperheroe(@RequestBody Superheroe superheroe) {
    	return superheroesService.updateSuperheroe(superheroe);
    }
    
 
}
