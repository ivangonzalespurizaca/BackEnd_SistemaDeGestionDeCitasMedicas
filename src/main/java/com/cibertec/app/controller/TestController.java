package com.cibertec.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.app.entity.Medico;
import com.cibertec.app.repository.MedicoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
    private MedicoRepository medicoRepository;

    /**
     * Prueba 1: existsByDni (Validación de Unicidad)
     * URL: GET http://localhost:8080/api/test/medicos/existe/dni/{dni}
     */
    @GetMapping("/existe/dni/{dni}")
    public boolean existeMedicoPorDni(@PathVariable String dni) {
        // Llama al método del repositorio: boolean existsByDni(String dni)
        System.out.println("Verificando existencia de DNI: " + dni);
        return medicoRepository.existsByDni(dni);
    }

    @GetMapping("/nombres/{nombre}")
    public List<Medico> buscarPorNombresParciales(@PathVariable String nombre) {
        // Llama al método: List<Medico> findByNombresStartingWithIgnoreCase(String nombre)
        System.out.println("Buscando médicos que empiecen por: " + nombre);
        return medicoRepository.findByNombresStartingWithIgnoreCase(nombre); 
    }
    
    @GetMapping("/dni/parcial/{dni}")
    public List<Medico> buscarPorDniParcial(@PathVariable String dni) {
        // Llama al método: List<Medico> findByDniStartingWith(String dni)
        System.out.println("Buscando médicos cuyo DNI empieza con: " + dni);
        return medicoRepository.findByDniStartingWith(dni);
    }
    
    
}
