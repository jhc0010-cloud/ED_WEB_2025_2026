package com.clinica.veterinaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ClinicaVeterinariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaVeterinariaApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }
}

