package com.cibertec.app.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cibertec.app.entity.Usuario;
import com.cibertec.app.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UsuarioRepository usuarioRepository;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Intentando login con username: " + username);

        Usuario usuario = usuarioRepository.findByUsername(username);
        System.out.println("Usuario encontrado en DB: " + usuario);

        if (usuario == null) throw new UsernameNotFoundException("Usuario no encontrado");

        String role;
        switch (usuario.getRol()) {
            case ADMINISTRADOR -> role = "ADMINISTRADOR"; 
            case RECEPCIONISTA -> role = "RECEPCIONISTA"; 
            case CAJERO -> role = "CAJERO";
            case MEDICO -> role = "MEDICO";
            default -> throw new IllegalArgumentException("Rol desconocido");
        }
        System.out.println("Rol asignado: " + role);

        UserDetails userDetails = User.builder()
                .username(usuario.getUsername())
                .password(usuario.getContrasenia())
                .roles(role)
                .build();

        System.out.println("UserDetails construido: " + userDetails);
        return userDetails;
    }
}
