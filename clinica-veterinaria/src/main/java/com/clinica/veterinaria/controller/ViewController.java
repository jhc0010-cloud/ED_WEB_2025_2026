package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping(value={"/"})
    public String home() {
        return "redirect:/index.html";
    }

    @GetMapping(value={"/favicon.ico"})
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.noContent().build();
    }
}
