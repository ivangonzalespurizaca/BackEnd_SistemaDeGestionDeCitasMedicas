package com.cibertec.app.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cibertec.app.entity.Usuario;
import com.cibertec.app.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {
    private final UsuarioRepository usuarioRepository;

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual(@AuthenticationPrincipal User user) {
        if (user != null) {
            return usuarioRepository.findByUsername(user.getUsername()); 
        }
        return null;
    }
}
