# Clínica Veterinaria

Proyecto DAW de gestión de clínica veterinaria con backend en Spring Boot y frontend multipágina estático.

## Tecnologías

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- HTML
- CSS
- Bootstrap
- JavaScript

## Estructura

- `src/main/java` -> backend
- `src/main/resources/static` -> frontend multipágina
- `database/` -> scripts SQL
- `docs/` -> documentación

## Documentación útil

- `docs/instalacion/manual-git-intellij-equipos.md` -> tutorial de Git e IntelliJ para alumnado y jefes de equipo

## Ejecución

1. Crear la base de datos MySQL.
2. Configurar `src/main/resources/application.properties`.
3. Ejecutar `ClinicaVeterinariaApplication` desde IntelliJ.
4. Abrir [http://localhost:8080/login.html](http://localhost:8080/login.html).

## Estado actual

Este repositorio incluye una base estructural completa del proyecto:

- Paquetes organizados por capas.
- Entidades, DTOs, repositorios, servicios y controladores base.
- Frontend multipágina preparado por perfiles.
- Scripts SQL y documentación inicial.
