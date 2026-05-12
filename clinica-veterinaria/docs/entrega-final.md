# Documentacion de entrega final

Responsable documentacion: Giulshen Hueso - G8.

## Proyecto

Aplicacion web para la gestion de una clinica veterinaria con backend Spring Boot, base de datos relacional y frontend multipagina.

## Funcionalidades entregadas

| Rol | Funcionalidades |
| --- | --- |
| Administrador | Login, dashboard, gestion de usuarios y consulta de perfiles. |
| Auxiliar | Gestion de clientes, mascotas, citas, REIAC, pagos, recibos, recetas imprimibles y justificantes de cita. |
| Veterinario | Lista de espera, registro de consultas y recetas con varios medicamentos por consulta. |
| Cliente | Registro, citas disponibles, estado, historial, recetas y pagos. |

## Flujo principal

```text
Cliente solicita cita disponible
Auxiliar confirma la cita pendiente
Veterinario atiende la cita confirmada
Veterinario registra consulta y recetas
Auxiliar o cliente confirma el pago
Cliente consulta historial, recetas y pagos
```

## Tecnologias

- Java 21.
- Spring Boot 3.3.5.
- Spring Security.
- Spring Data JPA.
- MySQL/MariaDB.
- H2 para demo con perfil `dev`.
- HTML, CSS, Bootstrap 5 y JavaScript vanilla.
- JUnit 5 y Mockito.

## Ejecucion recomendada para demo

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Abrir:

```text
http://localhost:8080/login.html
```

## Usuarios demo

| Rol | Usuario | Contrasena |
| --- | --- | --- |
| Administrador | `admin` | `admin123` |
| Auxiliar | `auxiliar` | `auxiliar123` |
| Veterinario | `vet` | `vet123` |
| Cliente | `cliente` | `cliente123` |

## Pruebas de entrega

```powershell
.\mvnw.cmd test
```

Resultado esperado:

- `BUILD SUCCESS`.
- 13 tests ejecutados.
- 0 fallos.
- 0 errores.

## Documentos incluidos

- Manual de instalacion.
- Manuales de usuario por rol.
- Documentacion API REST.
- Diccionario de datos.
- Diagramas de clases y entidad-relacion.
- Plan de pruebas.
- Informe de pruebas.
- Reparto de responsables.
- Registro de errores, riesgos y mejoras.

## Criterios de aceptacion

- Cada rol accede solo a sus pantallas.
- El cliente no confirma citas: solo las solicita.
- El auxiliar confirma citas y pagos.
- El veterinario registra consultas y recetas.
- Una consulta puede tener varios medicamentos.
- El cliente puede ver sus recetas.
- Los documentos principales se pueden imprimir desde recepcion.

## Limitaciones conocidas

- REIAC esta simulado.
- La pasarela de pago es simulada.
- El perfil `dev` usa datos en memoria.
- MySQL depende de la configuracion local de `application.properties`.
