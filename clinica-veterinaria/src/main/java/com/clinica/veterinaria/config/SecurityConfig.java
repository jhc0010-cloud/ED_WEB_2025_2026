package com.clinica.veterinaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    // TODO FUNCION: Definir que rutas son publicas, que rutas requieren rol y como se realiza login/logout.
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/login.html",
                                "/register.html",
                                "/css/**",
                                "/js/**",
                                "/error/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/auxiliar/**").hasAnyRole("ADMIN", "AUXILIAR")
                        .requestMatchers("/veterinario/**").hasAnyRole("ADMIN", "VETERINARIO")
                        .requestMatchers("/cliente/**").hasAnyRole("ADMIN", "CLIENTE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                )
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    // TODO FUNCION: Proporcionar el codificador usado para guardar y comprobar contrasenas de forma segura.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

