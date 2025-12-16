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

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) throw new UsernameNotFoundException("Usuario no encontrado");

        String role = usuario.getRol().name();

        UserDetails userDetails = User.builder()
                .username(usuario.getUsername())
                .password(usuario.getContrasenia())
                .roles(role)
                .build();

        return userDetails;
    }
}
