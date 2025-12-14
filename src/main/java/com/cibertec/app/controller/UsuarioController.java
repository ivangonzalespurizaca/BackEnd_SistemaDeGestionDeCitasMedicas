package com.cibertec.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    
    @GetMapping("/administrador/home")
    public String admin() {
        return "administrador/inicio"; 
    }
    
    @GetMapping("/recepcionista/home")
    public String recepcionista() {
        return "recepcionista/inicio"; 
    }
    
    @GetMapping("/cajero/home")
    public String cajero () {
        return "cajero/inicio"; 
    }
    
    @GetMapping("/medico/home")
    public String medico () {
        return "medico/inicio"; 
    }
    
}
