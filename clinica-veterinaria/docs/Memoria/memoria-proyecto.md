# Memoria del proyecto

## Objetivo

La aplicacion permite gestionar una clinica veterinaria con cuatro perfiles:

- Administrador: usuarios y perfiles.
- Auxiliar: clientes, mascotas, citas y verificacion REIAC.
- Veterinario: lista de espera, consultas y recetas.
- Cliente: estado de consultas, historial y pagos.

## Decisiones de integracion

El proyecto tenia varias fuentes: PDF de casos de uso, SQL del DER original, markdown completo, wireframes HTML y un backend comentado. Se ha unificado tomando como base el proyecto Spring Boot existente en este repositorio.

La principal incongruencia estaba en la base de datos:

- El SQL externo usaba tablas como `propietario`, `mascota`, `veterinario` y `usuarios` con columnas `id_cuenta`.
- El backend actual usa una nomenclatura mas propia de JPA: `clientes`, `mascotas`, `veterinarios`, `citas`, `consultas`, `pagos`.

Para evitar romper el codigo Java, se mantiene la nomenclatura del backend y se documenta el SQL externo como referencia historica del DER, no como script directo de ejecucion.

## Arquitectura

```text
src/main/java/com/clinica/veterinaria
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```

El frontend esta en:

```text
src/main/resources/static
```

## Modelo funcional

Entidades principales:

- `Rol`
- `Usuario`
- `Cliente`
- `Veterinario`
- `Mascota`
- `Cita`
- `Consulta`
- `Receta`
- `Pago`
- `VerificacionReiac`

Relaciones principales:

- Un usuario tiene un rol.
- Un cliente puede estar asociado a un usuario.
- Una mascota pertenece a un cliente y puede tener veterinario asignado.
- Una cita une mascota y veterinario.
- Una consulta se registra sobre una cita.
- Una receta se registra sobre una consulta.
- Un pago se asocia a cliente y opcionalmente a consulta.

## Seguridad

Spring Security protege las rutas por rol:

- `/admin/**` y `/api/admin/**`: `ROLE_ADMIN`
- `/auxiliar/**`: `ROLE_ADMIN`, `ROLE_AUXILIAR`
- `/veterinario/**`: `ROLE_ADMIN`, `ROLE_VETERINARIO`
- `/cliente/**`: `ROLE_ADMIN`, `ROLE_CLIENTE`

El formulario de login usa `/login`. El registro publico crea usuarios con `ROLE_CLIENTE`.

## Frontend

Se ha sustituido el wireframe HTML gigante de clientes por una pantalla funcional y mantenible conectada al backend. Las pantallas principales tienen formularios y tablas reales:

- Administracion: usuarios y perfiles.
- Auxiliar: clientes, mascotas, citas y REIAC.
- Veterinario: lista de espera, consulta y recetas.
- Cliente: citas, estado, historial y pagos.

## Responsables

Los HTML principales incluyen comentarios con el responsable del modulo, por ejemplo:

```html
<!-- Responsable: Gonzalo Viedma / G4 Gestion de clientes -->
```

La tabla completa esta en `docs/responsables.md`.

## Estado de entrega

El proyecto compila correctamente con:

```powershell
.\mvnw.cmd test
```

Queda preparado para demo local con MySQL/MariaDB en `localhost:3306`.
