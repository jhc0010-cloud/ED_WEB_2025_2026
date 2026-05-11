package com.clinica.veterinaria.security;

// Responsable backend: Juan Hakram Huertas Chergui - G1, seguridad y autenticacion.
import com.clinica.veterinaria.entity.Usuario;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl
implements UserDetails {
    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.usuario.getRol() == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority(this.usuario.getRol().getNombre()));
    }

    public String getPassword() {
        return this.usuario.getPassword();
    }

    public String getUsername() {
        return this.usuario.getUsername();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.usuario.isActivo();
    }
}
