package com.developer.covidpractica.services;

import com.developer.covidpractica.entities.EstadoTurno;
import com.developer.covidpractica.entities.FechaTurno;
import com.developer.covidpractica.entities.Turno;
import com.developer.covidpractica.repositories.TurnoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private TurnoRepositorio turnoRepositorio;
    @Autowired
    public TurnoService(TurnoRepositorio turnoRepositorio) {
        this.turnoRepositorio = turnoRepositorio;
    }
    public List<Turno> getAll() {
        return turnoRepositorio.findAll();
    }
    
    public Page<Turno> getAllEstado(Pageable pageable, EstadoTurno estadoTurno) {
        return turnoRepositorio.findByEstado(pageable, estadoTurno);
    }
    
    public Page<Turno> getAllPageable(Pageable pageable) {
        return turnoRepositorio.findAll(pageable);
    }
    
    public Turno save(Turno turno) {

        return turnoRepositorio.save(turno);
    }

    public boolean exist(Long dni) {
        return turnoRepositorio.existsByDni(dni);
    }

    public boolean isPossible(Turno turno) {
    	 Turno turnoDb = turnoRepositorio.findById(turno.getId()).get();
         return turnoDb.getDni().equals(turno.getDni()) || !exist(turno.getDni());
    }

    public Optional<Turno> findByDni(Long dni) {
    	return turnoRepositorio.findByDni(dni);
    }

    public Optional<Turno> findByDniToUpdate(Long dni) {
    	return turnoRepositorio.findByDni(dni);
    }

    public void unregister(Long dni) {
        Turno turno = turnoRepositorio.findByDni(dni).get();
        turno.setFechaTurno(null);
        turno.setEstadoTurno(EstadoTurno.BAJA);
        turnoRepositorio.save(turno);
    }

    public void loss(Long dni) {
        Turno turno = turnoRepositorio.findByDni(dni).get();
        turno.setEstadoTurno(EstadoTurno.PERDIDO);
        turnoRepositorio.save(turno);
    }

    public boolean isUnregister(Long dni) {
        Turno turno = turnoRepositorio.findByDni(dni).get();
        if(turno.getEstadoTurno() == EstadoTurno.BAJA) return true;
        return false;
    }
    

    public boolean isLoss(Long dni) {
        Turno turno = turnoRepositorio.findByDni(dni).get();
        if(turno.getEstadoTurno() == EstadoTurno.PERDIDO) return true;
        return false;
    }

    public Turno update(Turno turno) {
        Turno turnoToUpdate = turnoRepositorio.findById(turno.getId()).get();
        turnoToUpdate.setDni(turno.getDni());
        turnoToUpdate.setNombres(turno.getNombres());
        turnoToUpdate.setApellidos(turno.getApellidos());
        turnoToUpdate.setTelefono(turno.getTelefono());
        turnoToUpdate.setEmail(turno.getEmail());
        turnoToUpdate.setDireccion(turno.getDireccion());
        turnoToUpdate.setVacuna(turno.getVacuna());
        return turnoRepositorio.save(turnoToUpdate);
    }
    
    public boolean asignarTurno(FechaTurno fechaTurno) {
        Optional<Turno> turnoOptional = turnoRepositorio.findByDni(fechaTurno.getDni());
      
        Date fecha = fechaTurno.getFecha();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.DATE, 1);
        fecha = c.getTime();
        
        
        if(!turnoOptional.isPresent()) return  false;
        //Date now = new Date();
        //if(now.before(fechaTurno.getFecha())) return false;
        Turno turno = turnoOptional.get();
        turno.setFechaTurno(fecha);
        turno.setEstadoTurno(EstadoTurno.ASIGNADO);
        this.turnoRepositorio.save(turno);
        return true;       
        
    }

    public void inactivarTurno(Long dni) {
        Turno turno = turnoRepositorio.findByDni(dni).get();
        turno.setEstadoTurno(EstadoTurno.INACTIVO);
        turnoRepositorio.save(turno);
    }
    
}
