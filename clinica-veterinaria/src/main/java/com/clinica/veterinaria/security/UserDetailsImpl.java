package com.clinica.veterinaria.security;

import com.clinica.veterinaria.entity.Usuario;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    // TODO FUNCION: Devolver los roles o permisos reales del usuario autenticado.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    // TODO FUNCION: Entregar a Spring Security la contrasena cifrada almacenada del usuario.
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    // TODO FUNCION: Entregar a Spring Security el nombre de usuario usado para autenticarse.
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    // TODO FUNCION: Indicar si la cuenta sigue vigente y puede iniciar sesion.
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // TODO FUNCION: Indicar si la cuenta no esta bloqueada por seguridad o administracion.
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // TODO FUNCION: Indicar si las credenciales del usuario siguen siendo validas.
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // TODO FUNCION: Indicar si el usuario esta activo dentro del sistema.
    public boolean isEnabled() {
        return usuario.isActivo();
    }
}

