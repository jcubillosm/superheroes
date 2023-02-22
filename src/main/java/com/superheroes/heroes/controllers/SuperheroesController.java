package com.superheroes.heroes.controllers;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.superheroes.heroes.models.Superheroe;
import com.superheroes.heroes.services.SuperheroesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/superheroes")
public class SuperheroesController {
	@Autowired
	SuperheroesService superheroesService;
	@Autowired
	CacheManager cacheManager;
	
	@Operation(summary = "Obtiene un listado de superheroes")
    @GetMapping
    @ResponseStatus(OK)
    public List<Superheroe> getAllSuperheroes() {
		log.info("SuperheroesController: getAllSuperheroes");
        return superheroesService.getAllSuperheroes();
    }
    @Operation(summary = "Obtiene un superheroe por id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSuperheroeById(@Parameter(description = "id del superheroe a mostrar")  @PathVariable Long id) {
        log.info("SuperheroesController: getSuperheroeById");
        return ResponseEntity.ok(superheroesService.getSuperheroeById(id));
    }
    @Operation(summary = "Obtiene un listado de superheroes que coincide con la cadena dada")
    @GetMapping("/findByPattern/{pattern}")
    public List<Superheroe> getSuperheroeByPattern(@Parameter(description = "Cadena a buscar en el nombre de los superheroes") @PathVariable String pattern) {
    	log.info("SuperheroesController: getSuperheroeByPattern");
        return superheroesService.getSuperheroeByPattern(pattern);
    }
    @Operation(summary = "Modifica el nombre de un superheroe por el id")
    @PutMapping
    @ResponseStatus(OK)
    public Superheroe updateSuperheroe(@RequestBody Superheroe superheroe) {
    	log.info("SuperheroesController: updateSuperheroe");
    	return superheroesService.modifySuperheroe(superheroe);
    }
    @Operation(summary = "Borra un superheroe indicado por el id")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@Parameter(description = "id del superheroe a eliminar") @PathVariable Long id) {
    	log.info("SuperheroesController: deleteById");
    	superheroesService.deleteById(id);
    }
    @DeleteMapping("/cache")
    @ResponseStatus(NO_CONTENT)
    public void limpiarCacheSuperheroes() {
    	log.info("SuperheroesController: limpiarCacheSuperheroes");
    	Objects.requireNonNull(cacheManager.getCache("superheroes")).clear();
    	Objects.requireNonNull(cacheManager.getCache("superheroe")).clear();
    }
}