package com.superheroes.heroes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.superheroes.heroes.models.Superheroe;

@Repository
public interface SuperheroesRepository extends JpaRepository<Superheroe, Long> {

   @Query(value = "FROM Superheroe s WHERE LOWER(s.name) like LOWER(?1)")
    List<Superheroe> findByPattern(String pattern);


}

