package com.developer.covidpractica.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.developer.covidpractica.entities.EstadoTurno;
import com.developer.covidpractica.entities.Turno;

import java.util.Optional;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, Long> {
    
	public boolean existsByDni(Long dni);
    
    public Optional<Turno> findByDni(Long dni);
    
    @Query("SELECT t FROM Turno t WHERE t.estadoTurno != 'INACTIVO'")
    public Page<Turno> findAll(Pageable pagable);
    
    @Query("SELECT t FROM Turno t WHERE t.estadoTurno = ?1")
    public Page<Turno> findByEstado(Pageable pagable, EstadoTurno estadoTurno);
   
}