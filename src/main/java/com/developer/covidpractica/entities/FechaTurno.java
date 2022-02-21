package com.developer.covidpractica.entities;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class FechaTurno {
    @NotNull
    private Long dni;
    private String nombres;
    private String apellidos;
    private EstadoTurno estadoTurno;
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fecha;

    public FechaTurno(Long dni, String nombres, String apellidos, EstadoTurno estadoTurno, Date fecha) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estadoTurno = estadoTurno;
        this.fecha = fecha;
    }

    public FechaTurno() {
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public EstadoTurno getEstadoTurno() {
        return estadoTurno;
    }

    public void setEstadoTurno(EstadoTurno estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}