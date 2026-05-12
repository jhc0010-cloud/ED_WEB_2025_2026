# Modelo de base de datos

Responsable documentacion BBDD: Reda El QOURCHI - G8.

El modelo final usado por la aplicacion Spring Boot normaliza el DER original a nombres plurales coherentes con las entidades Java.

```mermaid
erDiagram
    ROLES ||--o{ USUARIOS : asigna
    USUARIOS ||--o| CLIENTES : cuenta
    USUARIOS ||--o| VETERINARIOS : cuenta
    CLIENTES ||--o{ MASCOTAS : propietario
    VETERINARIOS ||--o{ MASCOTAS : atiende
    MASCOTAS ||--o{ CITAS : agenda
    VETERINARIOS ||--o{ CITAS : atiende
    CITAS ||--o| CONSULTAS : genera
    CONSULTAS ||--o| RECETAS : prescribe
    CLIENTES ||--o{ PAGOS : realiza
    CONSULTAS ||--o{ PAGOS : liquida
```

## Incongruencia resuelta

El SQL externo `BaseDatosClinicaVeterinaria.sql` usa `propietario`, `mascota`, `veterinario`, `ficha` y `usuarios.id_cuenta`. El backend actual usa `clientes`, `mascotas`, `veterinarios`, `consultas` y `usuarios.id`.

Se mantiene el modelo del backend porque:

- Ya esta integrado con JPA.
- Evita cambios masivos de entidades y controladores.
- Es mas consistente con el frontend multipagina actual.

El esquema ejecutable esta en:

```text
src/main/resources/schema.sql
```
