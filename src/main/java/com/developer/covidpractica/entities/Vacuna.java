package com.developer.covidpractica.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "vacunas")
public class Vacuna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "El nombre de la vacuna no puede estar vacío")
    @Size(min = 3, max = 15, message = "El nombre de la vacuna debe tener entre 3 y 15 caracteres")
    private String nombre;
    private String descripcion;
    @Column(nullable = false)
    @Min(value = 1, message = "Número de dosis debe ser mayor a cero")
    private int numeroDosis;
    @Column(nullable = false)
    @Min(value = 1, message = "Días entre dosis debe ser mayor a cero")
    private int diaEntreDosis;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EstadoVacuna estado = EstadoVacuna.ALTA;
    @OneToMany(mappedBy="vacuna")
    private Set<Turno> turnos;

    public Vacuna(Long id,
                  String nombre,
                  String descripcion,
                  int numeroDosis,
                  int diaEntreDosis,
                  EstadoVacuna estado,
                  Set<Turno> turnos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numeroDosis = numeroDosis;
        this.diaEntreDosis = diaEntreDosis;
        this.estado = estado;
        this.turnos = turnos;
    }

    public Vacuna() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroDosis() {
        return numeroDosis;
    }

    public void setNumeroDosis(int numeroDosis) {
        this.numeroDosis = numeroDosis;
    }

    public int getDiaEntreDosis() {
        return diaEntreDosis;
    }

    public void setDiaEntreDosis(int diaEntreDosis) {
        this.diaEntreDosis = diaEntreDosis;
    }

    public EstadoVacuna getEstado() {
        return estado;
    }

    public void setEstado(EstadoVacuna estado) {
        this.estado = estado;
    }

    public Set<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }
}
