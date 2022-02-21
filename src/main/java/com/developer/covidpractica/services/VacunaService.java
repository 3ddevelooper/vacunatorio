package com.developer.covidpractica.services;

import com.developer.covidpractica.entities.EstadoVacuna;
import com.developer.covidpractica.entities.Vacuna;
import com.developer.covidpractica.repositories.VacunaRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
//import java.util.Objects;
import java.util.Optional;

@Service
public class VacunaService {
    private VacunaRepositorio vacunaRepositorio;

    @Autowired
    public VacunaService(VacunaRepositorio vacunaRepositorio) {
        this.vacunaRepositorio = vacunaRepositorio;
    }

    public Optional<Vacuna> findById(Long id) {
        return vacunaRepositorio.findById(id);
    }
    
    public boolean existById(Long id) {
        return vacunaRepositorio.existsById(id);
    }


    public boolean existByNombre(String nombre) {
        return vacunaRepositorio.existsByNombre(nombre);
    }

    public Page<Vacuna> getAllPageable(Pageable pageable) {
        return vacunaRepositorio.findAll(pageable);
    }
    
    public List<Vacuna> getAll() {
        return vacunaRepositorio.findAll();
    }

   
    public Vacuna save(Vacuna vacuna) {
        return vacunaRepositorio.save(vacuna);
    }
    
   
    public void update(Vacuna vacuna) {
        Vacuna vacunaToUpdate = vacunaRepositorio.findById(vacuna.getId()).get();
        vacunaToUpdate.setId(vacuna.getId());
        vacunaToUpdate.setNombre(vacuna.getNombre());
        vacunaToUpdate.setDescripcion(vacuna.getDescripcion());
        vacunaToUpdate.setNumeroDosis(vacuna.getNumeroDosis());
        vacunaToUpdate.setDiaEntreDosis(vacuna.getDiaEntreDosis());
        vacunaRepositorio.save(vacunaToUpdate);
    }
    
    public void baja(Long id) {
        Vacuna vacuna = vacunaRepositorio.findById(id).get();
        vacuna.setEstado(EstadoVacuna.BAJA);
        vacunaRepositorio.save(vacuna);
    }

    public boolean isPossible(Vacuna vacuna) {
        Vacuna vacunaDb = vacunaRepositorio.findById(vacuna.getId()).get();
        return vacunaDb.getNombre().equalsIgnoreCase(vacuna.getNombre()) || !existByNombre(vacuna.getNombre());
    }

	
}