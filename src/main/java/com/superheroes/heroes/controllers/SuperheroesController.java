package com.superheroes.heroes.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.superheroes.heroes.models.Superheroe;
import com.superheroes.heroes.services.SuperheroesService;

@RestController
@RequestMapping("/superheroes")
public class SuperheroesController {
	@Autowired
	SuperheroesService service;
    @GetMapping
    @ResponseStatus(OK)
    public List<Superheroe> getAllSuperheroes() {
        return this.service.getAllSuperheroes();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSuperheroeById(@PathVariable Long id) {
        Superheroe heroe = this.service.getSuperheroedById(id);
        return ResponseEntity.ok(heroe);
    }
    
    
 
}
