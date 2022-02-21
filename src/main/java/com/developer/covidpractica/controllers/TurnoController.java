package com.developer.covidpractica.controllers;

import com.developer.covidpractica.entities.Turno;
import com.developer.covidpractica.entities.Vacuna;
import com.developer.covidpractica.services.TurnoService;
import com.developer.covidpractica.services.VacunaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/turno")
public class TurnoController {

    private final TurnoService turnoService;
    private final VacunaService vacunaService;

    @Autowired
    public TurnoController(TurnoService turnoService, VacunaService vacunaService) {
        this.turnoService = turnoService;
        this.vacunaService = vacunaService;
    }

    @GetMapping("/solicitud")
    public String solicitud(Model model) {
        model.addAttribute("title", "Solicitud de Turno");
        model.addAttribute("turno", new Turno());
        return "solicitud";
    }

    @PostMapping("/guardar-turno")
    public String guardarTurno(@Valid @ModelAttribute("turno") Turno turno,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Solicitud de Turno");
            return "solicitud";
        }
        if(turnoService.exist(turno.getDni())) {
            bindingResult.rejectValue("dni", "error.dni", "El dni ya existe.");
            model.addAttribute("title", "Solicitud de Turno");
            return "solicitud";
        }
        turnoService.save(turno);
        model.addAttribute("title", "Respuesta");
        model.addAttribute("condicion", 1);
        model.addAttribute("resp", "Turno creado correctamente :)");
        return "respuesta";
    }

    @GetMapping("/consulta")
    public String buscar(@RequestParam(value="dni") Long dni, Model model) {

        if(!turnoService.exist(dni)) {
            model.addAttribute("title", "Respuesta");
            model.addAttribute("condicion", 0);
            model.addAttribute("resp", "El dni ingresado no pidió turno :(");
            return "respuesta";
        }

        if(turnoService.isUnregister(dni)) {
            model.addAttribute("title", "Respuesta");
            model.addAttribute("condicion", 0);
            model.addAttribute("resp", "El dni ingresado dio de baja su turno :(");
            return "respuesta";
        }

        if(turnoService.isLoss(dni)) {
            model.addAttribute("title", "Respuesta");
            model.addAttribute("condicion", 0);
            model.addAttribute("resp", "El dni ingresado notificó la perdida de turno," +
                    " pronto se le asignará uno nuevo :(");
            return "respuesta";
        }

        Optional<Turno> turnoOptional = turnoService.findByDni(dni);
        model.addAttribute("title", "Consultar Turno");
        model.addAttribute("turno", turnoOptional.get());
        return "consulta";
    }

    @GetMapping("/perdida")
    public String perdida(Model model) {
        model.addAttribute("title", "Perdida de Turno");
        return "perdida";
    }

    @GetMapping("/baja")
    public String darBaja(@RequestParam(value="dni") Long dni, Model model) {
        turnoService.unregister(dni);
        model.addAttribute("title", "Respuesta");
        model.addAttribute("condicion", 1);
        model.addAttribute("resp", "El Turno fue dado de baja :(");
        return "respuesta";
    }

    @GetMapping("/notificar-perdida")
    public String perdiTurno(@RequestParam(value="dni") Long dni, Model model) {
        if(!turnoService.exist(dni)) {
            model.addAttribute("title", "Respuesta");
            model.addAttribute("condicion", 0);
            model.addAttribute("resp", "El dni ingresado no pidió turno :(");
            return "respuesta";
        }
        turnoService.loss(dni);
        model.addAttribute("title", "Respuesta");
        model.addAttribute("condicion", 1);
        model.addAttribute("resp", "La perdida de turno fue notificada correctamente :(");
        return "respuesta";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam(value="dni") Long dni, Model model) {
        Optional<Turno> turnoOptional = turnoService.findByDniToUpdate(dni);
        model.addAttribute("title", "Editar Datos");
        model.addAttribute("turno", turnoOptional.get());
        return "editar";
    }

    @PostMapping("/editar-datos")
    public String actualizar(@Valid @ModelAttribute("turno") Turno turno,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Editar Datos");
            return "editar";
        }
        if(!turnoService.isPossible(turno)) {
            bindingResult.rejectValue("dni", "error.dni", "El dni ya existe.");
            List<Vacuna> vacunas = vacunaService.getAll();
            model.addAttribute("title", "Editar Datos");
            model.addAttribute("vacunas", vacunas);
            return "editar";
        }
        turnoService.update(turno);
        model.addAttribute("title", "Respuesta");
        model.addAttribute("condicion", 1);
        model.addAttribute("resp", "Turno modificado correctamente :)");
        return "respuesta";
    }
    
    @ModelAttribute("vacunas")
    private List<Vacuna> getVacunas() {
        return vacunaService.getAll();
    }
}

