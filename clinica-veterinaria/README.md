# Clinica Veterinaria

Proyecto DAW de gestion de clinica veterinaria con backend Spring Boot, seguridad por roles, base de datos MySQL y frontend multipagina servido desde `src/main/resources/static`.

## Estado funcional

El proyecto queda integrado con los casos de uso principales:

- Login y registro de usuarios cliente.
- Roles reales: `ADMIN`, `AUXILIAR`, `VETERINARIO` y `CLIENTE`.
- Gestion de usuarios y perfiles desde administracion.
- Gestion de clientes, mascotas, citas y verificacion REIAC desde auxiliar.
- Lista de espera, consultas y recetas desde veterinario.
- Consulta de estado, historial y pagos desde cliente.

## Tecnologias

- Java 21
- Spring Boot 3.3.5
- Spring Security
- Spring Data JPA
- MySQL / MariaDB
- HTML, Bootstrap 5, CSS y JavaScript vanilla

## Ejecucion

### Opcion rapida de demo sin MySQL

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Esta opcion usa H2 en memoria y carga los datos demo automaticamente.

### Opcion MySQL de entrega

1. Crear la base de datos:

```sql
CREATE DATABASE IF NOT EXISTS clinica_veterinaria;
```

2. Revisar credenciales en `src/main/resources/application.properties`.
3. Ejecutar:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

4. Abrir `http://localhost:8080/login.html`.

## Usuarios demo

| Rol | Usuario | Contrasena |
| --- | --- | --- |
| Administrador | `admin` | `admin123` |
| Auxiliar | `auxiliar` | `auxiliar123` |
| Veterinario | `vet` | `vet123` |
| Cliente | `cliente` | `cliente123` |

## Rutas principales

- `/admin/dashboard.html`
- `/auxiliar/dashboard.html`
- `/veterinario/dashboard.html`
- `/cliente/dashboard.html`

## Documentacion

- `docs/indice-documentacion.md`
- `docs/entrega-final.md`
- `docs/README_BACKEND.md`
- `docs/README_FRONTEND.md`
- `docs/manual-usuario/indice-manuales.md`
- `docs/manual-usuario/manual-admin.md`
- `docs/manual-usuario/manual-auxiliar.md`
- `docs/manual-usuario/manual-veterinario.md`
- `docs/manual-usuario/manual-cliente.md`
- `docs/api/documantacion-api-rest.md`
- `docs/bd/diccionario-datos.md`
- `docs/diseno/diagrama-clases.md`
- `docs/diseno/diagrama-entidad-relacion.md`
- `docs/instalacion/manual-instalacion.md`
- `docs/Memoria/memoria-proyecto.md`
- `docs/pruebas/plan-pruebas.md`
- `docs/pruebas/informe-pruebas.md`
- `docs/pruebas/proyecto_clinica_veterinaria_errores.md`
- `docs/responsables.md`

## Nota de integracion

La rama `main` local contenia una version minima de `auxiliar/clientes.html`. La rama actual ya traia un wireframe muy grande de esa pantalla; se ha sustituido por una version funcional, conectada a `/api/clientes`, mas mantenible y coherente con el resto del frontend multipagina.
