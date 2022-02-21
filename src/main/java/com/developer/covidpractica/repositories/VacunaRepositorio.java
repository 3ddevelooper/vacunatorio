package com.developer.covidpractica.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.developer.covidpractica.entities.Vacuna;

@Repository
public interface VacunaRepositorio extends JpaRepository<Vacuna, Long> {
	
	 public boolean existsByNombre(String nombre);
	
	 public boolean existsById(Long id);
	 
	 @Query("SELECT v FROM Vacuna v WHERE v.estado = 'ALTA'")
	  public Page<Vacuna> findAll(Pageable pageable);
}