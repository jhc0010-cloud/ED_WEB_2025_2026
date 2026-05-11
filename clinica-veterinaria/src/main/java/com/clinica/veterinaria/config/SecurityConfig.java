package com.clinica.veterinaria.config;

// Responsable backend: Juan Hakram Huertas Chergui - G1, configuracion backend.
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/login.html", "/register.html", "/favicon.ico", "/css/**", "/js/**", "/error/**").permitAll()
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                .requestMatchers("/api/auth/me").authenticated()
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/auxiliar/**", "/api/auxiliar/**").hasAnyRole("ADMIN", "AUXILIAR")
                .requestMatchers("/veterinario/**").hasAnyRole("ADMIN", "VETERINARIO")
                .requestMatchers("/cliente/**", "/api/portal/**").hasAnyRole("ADMIN", "CLIENTE")
                .requestMatchers("/api/clientes/**", "/api/mascotas/**", "/api/reiac/**").hasAnyRole("ADMIN", "AUXILIAR")
                .requestMatchers("/api/citas/**").hasAnyRole("ADMIN", "AUXILIAR", "VETERINARIO")
                .requestMatchers(HttpMethod.GET, "/api/recetas/**").hasAnyRole("ADMIN", "AUXILIAR", "VETERINARIO")
                .requestMatchers("/api/consultas/**", "/api/recetas/**").hasAnyRole("ADMIN", "VETERINARIO")
                .requestMatchers("/api/veterinarios/**").hasAnyRole("ADMIN", "AUXILIAR", "VETERINARIO", "CLIENTE")
                .requestMatchers("/api/pagos/**").hasAnyRole("ADMIN", "AUXILIAR")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(roleSuccessHandler())
                .permitAll())
            .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login.html?logout"))
            .exceptionHandling(ex -> ex.accessDeniedHandler((AccessDeniedHandler)accessDeniedHandler))
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler roleSuccessHandler() {
        return (request, response, authentication) -> {
            String target = authentication.getAuthorities().stream()
                .map(Object::toString)
                .filter(role -> role.startsWith("ROLE_"))
                .findFirst()
                .map(role -> switch (role) {
                    case "ROLE_ADMIN" -> "/admin/dashboard.html";
                    case "ROLE_AUXILIAR" -> "/auxiliar/dashboard.html";
                    case "ROLE_VETERINARIO" -> "/veterinario/dashboard.html";
                    case "ROLE_CLIENTE" -> "/cliente/dashboard.html";
                    default -> "/index.html";
                })
                .orElse("/index.html");
            response.setStatus(HttpServletResponse.SC_FOUND);
            response.setHeader("Location", target);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
