package com.developer.covidpractica.controllers;

import com.developer.covidpractica.entities.EstadoTurno;
import com.developer.covidpractica.entities.FechaTurno;
import com.developer.covidpractica.entities.Turno;
import com.developer.covidpractica.entities.Vacuna;
import com.developer.covidpractica.services.TurnoService;
import com.developer.covidpractica.services.VacunaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.lang.String;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller()
@RequestMapping("/admin")
public class AdminController {

    private final VacunaService vacunaService;
    private final TurnoService turnoService;

    @Autowired
    public AdminController(VacunaService vacunaService, TurnoService turnoService) {
        this.vacunaService = vacunaService;
        this.turnoService = turnoService;
    }

    @GetMapping("/vacunas")
	public String vacunas(@RequestParam Map<String, Object> params, Model model) {
		/*Page<Vacuna> vacunas = vacunaService.getAllPageable(PageRequest.of(page, 9));
		model.addAttribute("title", "Vacunas");
		model.addAttribute("vacunas", vacunas.getContent());*/
		
		int page = params.get("page") != null ? (Integer.parseInt(params.get("page").toString())-1) : 0;
        Page<Vacuna> vacunas = vacunaService.getAllPageable(PageRequest.of(page, 10));
        int totalPage = vacunas.getTotalPages();
        if(totalPage > 0) {
        	List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
        	model.addAttribute("pages", pages);
        }
        model.addAttribute("title", "Vacunas");
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
        model.addAttribute("vacunas", vacunas.getContent());
		
		return "admin/vacunas";
	}
    
    
    
    @GetMapping({"", "/turnos"})
    public String turnos(@RequestParam Map<String, Object> params, Model model) {
    	
    	Page<Turno> turnos = null;
    	int page = params.get("page") != null ? (Integer.parseInt(params.get("page").toString())-1) : 0;
    	
    	String estadoParams = params.get("estado") != null ? params.get("estado").toString() : "TODOS";
    	
    	if(params.get("estado") == null || Objects.equals(params.get("estado").toString(), "TODOS")) {
    		turnos = turnoService.getAllPageable(PageRequest.of(page, 10));
    		 
    	}else {
    		EstadoTurno estado = EstadoTurno.valueOf(params.get("estado").toString());
    		turnos = turnoService.getAllEstado(PageRequest.of(page, 10), estado);
    		 
    	}
        
        int totalPage = turnos.getTotalPages();
        if(totalPage > 0) {
        	List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
        	model.addAttribute("pages", pages);
        	
        }
        
        model.addAttribute("title", "Turnos");
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
        model.addAttribute("estado", estadoParams);
        model.addAttribute("turnos", turnos.getContent());
        return "admin/turnos";
    }
    
    
    
	@GetMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("title", "Nueva Vacuna");
		model.addAttribute("vacuna", new Vacuna());
		return "admin/form-agregar-vacuna";
	}

	@PostMapping("/guardar-vacuna")
	public String guardarVacuna(@Valid @ModelAttribute("vacuna") Vacuna vacuna, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Nueva Vacuna");
			return "/admin/form-agregar-vacuna";
		}
		if (vacunaService.existByNombre(vacuna.getNombre())) {
			bindingResult.rejectValue("nombre", "error.nombre", "El nombre de la vacuna ya existe.");
			model.addAttribute("title", "Nueva Vacuna");
			return "/admin/form-agregar-vacuna";
		}
		vacunaService.save(vacuna);
		model.addAttribute("title", "Vacunas");
		return "redirect:/admin/vacunas";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Optional<Vacuna> vacunaOptional = vacunaService.findById(id);
		model.addAttribute("title", "Editar vacuna");
		model.addAttribute("vacuna", vacunaOptional.get());
		return "admin/form-editar-vacuna";
	}
	
	
	@PostMapping("/modificar-vacuna")
	public String modificar(@Valid @ModelAttribute Vacuna vacuna, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Editar vacuna");
			return "admin/form-editar-vacuna";
		}
		if (!vacunaService.isPossible(vacuna)) {
			bindingResult.rejectValue("nombre", "error.nombre", "La vacuna ya existe en la base de datos");
			model.addAttribute("title", "Editar vacuna");
			return "/admin/form-editar-vacuna";
		}
		vacunaService.update(vacuna);
		return "redirect:/admin/vacunas";
	}
	
	@GetMapping("/baja-vacuna")
    public String baja(@RequestParam("id") Long id) {
        vacunaService.baja(id);
        return "redirect:/admin/vacunas";
    }

	@PostMapping("/asignar-turno")
	public String asignarTurno(@ModelAttribute FechaTurno fechaTurno, Model model) {
		
		Date now  =  new Date();
		
		if(fechaTurno.getFecha().before(now)){
			
			boolean respuesta = false;
			System.out.print(respuesta); 
			model.addAttribute("title", "Asignar Turno");
			model.addAttribute("condicion", 0);
			model.addAttribute("resp", "No se puede ingresar una fecha anterior o igual al dia actual");
		}
		
		
		if(fechaTurno.getFecha().after(now)) {
			boolean respuesta = turnoService.asignarTurno(fechaTurno);
			System.out.print(respuesta);
			model.addAttribute("title", "Asignar Turno");
			model.addAttribute("condicion", 1);
			model.addAttribute("resp", "Turno asignado correctamente :)");
		}
		
		 return "admin/respuestaadmin";
	}
	
	@GetMapping("/inactivar-turno")
	public String inactivar(@RequestParam("dni") Long dni) {
		turnoService.inactivarTurno(dni);
		return "redirect:/admin/turnos";
	}
	
	@GetMapping("/asignar-turno/{dni}")
	public String asignar(@PathVariable Long dni ,Model model) {
		Optional<Turno> turnoOptional = turnoService.findByDni(dni);
		Turno turno = turnoOptional.get();
		model.addAttribute("title", "Asignar Turno");
		model.addAttribute("fechaTurno", new FechaTurno(
				turno.getDni(),
				turno.getNombres(),
				turno.getApellidos(),
				turno.getEstadoTurno(),
				turno.getFechaTurno()
		));
		return "admin/asignar-turno";
	}
	
	
	
	@GetMapping("/busqueda")
    public String busqueda(@RequestParam (value = "dnibuscar", required = false) Long dni, Model model) {

		if (dni == null) {
			model.addAttribute("title", "Busqueda ");
			model.addAttribute("condicion", 0);
			model.addAttribute("resp", "Debe ingresar un dni valido");
			return "admin/respuestaadmin";
		}
		
        if(!turnoService.exist(dni)) {
            model.addAttribute("title", "Busqueda ");
            model.addAttribute("condicion", 0);
            model.addAttribute("resp", "El dni ingresado no pidió turno :(");
            return "admin/respuestaadmin";
        }
        

	    Optional<Turno> turnoOptional = turnoService.findByDni(dni);	
        model.addAttribute("title", "Resultado de búsqueda");
        model.addAttribute("turnos", turnoOptional.get());
        return "admin/busqueda";
    }
  
}

