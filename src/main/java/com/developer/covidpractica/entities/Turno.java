package com.developer.covidpractica.entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El campo dni no puede estar vacío.")
    @Digits(integer = 8, fraction = 0, message = "El campo dni debe tener 8 caracteres.")
    @Column(nullable = false, unique = true)
    private Long dni;
    @NotBlank(message = "El campo nombres no puede estar vacío.")
    @Size(max = 100, min = 3, message = "El campo nombre debe tener como mínimo 3 caracteres.")
    @Column(nullable = false)
    private String nombres;
    @NotBlank(message = "El campo apellidos no puede estar vacío.")
    @Size(max = 100, min = 3, message = "El campo nombre debe tener como mínimo 3 caracteres.")
    @Column(nullable = false)
    private String apellidos;
    @NotBlank(message = "El campo telefono no puede estar vacío.")
    @Size(max = 15, message = "Demasiados caracteres para teléfono.")
    @Column(nullable = false)
    private String telefono;
    private String email;
    private String direccion;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud= new Date();
    @Temporal(TemporalType.DATE)
    private Date fechaTurno ;
    @Enumerated(value = EnumType.STRING)
    private EstadoTurno estadoTurno= EstadoTurno.PENDIENTE;
    @ManyToOne()
    @JoinColumn(name="id_vacuna", nullable = false)
    private Vacuna vacuna;
    

    public Turno(Long id,
                 Long dni,
                 String nombres,
                 String apellidos,
                 String telefono,
                 String email,
                 String direccion,
                 Date fechaSolicitud,
                 Date fechaTurno,
                 EstadoTurno estadoTurno,
                 Vacuna vacuna) {
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaTurno = fechaTurno;
        this.estadoTurno = estadoTurno;
        this.vacuna = vacuna;
    }

    public Turno() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(Date fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public EstadoTurno getEstadoTurno() {
        return estadoTurno;
    }

    public void setEstadoTurno(EstadoTurno estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }


    
}
