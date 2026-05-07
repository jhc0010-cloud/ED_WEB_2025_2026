package com.clinica.veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    // TODO FUNCION: Redirigir la raiz de la web a la pagina principal estatica.
    public String home() {
        return "redirect:/index.html";
    }
}

